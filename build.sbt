name := "demo_filesystem"
ThisBuild / version := "1.0.0"
ThisBuild / organization := "tech.oheldarkaa"
ThisBuild / scalaVersion := "2.13.6"

lazy val demo_filesystem = (project in file("."))
  .settings(
    assembly / mainClass := Some("tech.oheldarkaa.demo.filesystem.FileSystem"),
    assembly / assemblyJarName := s"demo_filesystem.jar"
  )