package iad.sbt.util

import sbt._

object Paths {
  case class PathType(value: String)
  val relativeToBaseDirectory = PathType("relativeToBaseDirectory")
  val relativeToRepoRoot = PathType("relativeToRepoRoot")

  def repoRoot(baseDir: File): File = {
    var currentDir = baseDir.getAbsoluteFile

    while (currentDir != null && !(currentDir / ".git").exists()) {
      currentDir = currentDir.getParentFile
    }

    if (currentDir == null) {
      throw new RuntimeException("Unable to locate repo root from " + baseDir.getAbsolutePath)
    }

    currentDir
  }
}
