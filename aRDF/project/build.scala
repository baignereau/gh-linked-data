import sbt._
import Keys._

object BuildSettings {

  val logger = ConsoleLogger()

  val buildSettings = Defaults.defaultSettings ++ Seq (
    organization := "org.w3",
    version      := "0.1",
    scalaVersion := "2.9.1",
    scalaHome    := Some {
      val bin = file("bin")
      def latest =
        (bin.listFiles.toList filter {
          _.getName startsWith "scala-2.10.0.r" } sortWith {
            _.getName > _.getName })
        .headOption
      latest match {
        case Some(scala) => {
          logger.info("found nightly build of scala compiler: %s" format scala.getName)
          scala
        }
        case None => {
          val scalaLang = "http://www.scala-lang.org/archives/downloads/distrib/files/nightly/distributions/"
          val jay = "http://jay.w3.org/~bertails/public/scala/"
          val baseURL = jay
          val scalaVersion = "scala-2.10.0.rdev-4005-2011-12-13-g278a225"
          val u = url("%s%s.zip" format (jay, scalaVersion))
          val zip = file("/tmp/%s.zip" format scalaVersion)
          logger.info("downloading %s" format u.toString)
          u #> zip !;
          Process("unzip -o %s -d %s" format (zip.getAbsolutePath, bin.getAbsolutePath)) !;
          latest getOrElse sys.error("couldn't download and set the latest scala compiler") 
        }
      }
    },
    parallelExecution in Test := false,
    scalacOptions ++= Seq("-deprecation", "-unchecked", "-Yvirtpatmat", "-optimize")
  )

}

object YourProjectBuild extends Build {

  import BuildSettings._
  
  val mySettings = Seq(
    resolvers += "apache-repo-releases" at "http://repository.apache.org/content/repositories/releases/",
    libraryDependencies += "org.apache.jena" % "jena-arq" % "2.9.0-incubating",
    libraryDependencies += "com.novocode" % "junit-interface" % "0.8" % "test"
  )
  
  lazy val project = Project(
    id = "aRDF",
    base = file("."),
    settings = buildSettings ++ mySettings
  )


}

