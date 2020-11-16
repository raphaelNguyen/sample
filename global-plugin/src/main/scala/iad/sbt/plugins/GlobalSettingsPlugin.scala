package iad.sbt.plugins

import sbt.Keys._
import sbt._
import iad.sbt._
import iad.sbt.util.Paths
import sbt.io.AllPassFilter
import sbt.nio.Keys._

object GlobalSettingsPlugin extends AutoPlugin {
  override def trigger = allRequirements

  object autoImport {
    import scala.language.implicitConversions
    implicit def toProjectDecorator(project: Project): Projects.ProjectDecorator =
      new Projects.ProjectDecorator(project)

    val projects: Projects.type = Projects
    val ideOutputDirectory: SettingKey[Option[File]] = SettingKey[Option[File]](
      "ide-output-directory",
      "Output directory for IntelliJ to use. This is a predefined name that IntelliJ looks for."
    )
    val repoRoot = SettingKey[File](
      "iad-repo-root",
      "The root folder of the iad git repository."
    )
  }

  import autoImport._

  lazy val baseIntegradevSettings: Seq[Setting[_]] = Seq(
    Global / onChangedBuildSource := ReloadOnSourceChanges
  )

  lazy val baseIntegradevProjectSettings: Seq[Setting[_]] = Seq(
    organization := "com.integradev",
    version := sys.env.getOrElse("RELEASE_VERSION", "0.1-SNAPSHOT"),
    repoRoot := Paths.repoRoot(baseDirectory.value),
    scalaVersion := "2.12.11",
    ideOutputDirectory := Some((Compile / classDirectory).value),
    Test / ideOutputDirectory := Some((Test / classDirectory).value),
    Global / semanticdbEnabled := true
  )

  override def globalSettings = baseIntegradevSettings
  override def projectSettings = baseIntegradevProjectSettings
}
