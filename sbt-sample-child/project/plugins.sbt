lazy val `sbt-sample-child-build` = (project in file("."))
  .projectDependencies(projects.`specific-plugin`)
