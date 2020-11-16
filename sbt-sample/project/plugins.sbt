lazy val `sample-build` = (project in file("."))
  .projectDependencies(projects.`specific-plugin`)
