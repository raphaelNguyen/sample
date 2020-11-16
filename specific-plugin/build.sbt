lazy val `play-plugins` = (project in file("."))
  .enablePlugins(SbtPlugin)
  .projectDependencies(projects.`global-plugin`)

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.7.1")
