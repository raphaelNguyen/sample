lazy val `sbt-sample` = (project in file("."))
  .projectDependencies(projects.`sbt-sample-child`)
  .enablePlugins(PlayMinimalJava)
