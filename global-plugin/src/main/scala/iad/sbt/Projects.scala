package iad.sbt

import iad.sbt.util.Paths
import sbt._

object Projects {
  lazy val `sbt-sample-child` = IntegradevProject(_ / "sbt-sample-child")
  lazy val `global-plugin` = IntegradevProject(_ / "global-plugin")
  lazy val `specific-plugin` = IntegradevProject(_ / "specific-plugin")

  class ProjectDecorator(val project: Project) {
    def projectDependencies(projects: IntegradevProject*): Project = {
      val repoRoot = Paths.repoRoot(project.base)
      project
        .aggregate(projects.map(_.reference(repoRoot)): _*)
        .dependsOn(projects.map(_.dependency(repoRoot)): _*)
    }
  }

  class IntegradevProject private (val transform: File => File) {
    def reference(repoRoot: File) = RootProject(transform(repoRoot))
    def dependency(repoRoot: File) = reference(repoRoot) % "compile->compile;test->test"
  }

  object IntegradevProject {
    def apply(transform: File => File): IntegradevProject = {
      new IntegradevProject(transform)
    }
  }
}
