package iad.sbt.plugins

import com.typesafe.config.ConfigFactory
import play.sbt.PlayMinimalJava
import sbt._
import sbt.Keys._

object SpecificSettingsPlugin extends AutoPlugin {
  override def trigger = allRequirements
  override def requires = GlobalSettingsPlugin && PlayMinimalJava

  import com.typesafe.sbt.SbtNativePackager.autoImport.NativePackagerKeys
  import play.sbt.PlayImport.PlayKeys
  import play.sbt.routes.RoutesCompiler.autoImport.routes

  private lazy val appConf =
    ConfigFactory
      .load()
      .withFallback(
        ConfigFactory
          .parseFile(
            file(
              if (file("conf/application.conf").exists())
                "conf/application.conf"
              else
                "conf/reference.conf"
            )
          )
          .resolve()
      )

  override def projectSettings: Seq[Setting[_]] =
    Seq(
      NativePackagerKeys.maintainer := "Integrated Application Development Pty. Ltd.",
      Compile / routes / sources ++= (Compile / sourceDirectories).value
        .flatMap(file => (file ** "*.routes").get)
    )
}
