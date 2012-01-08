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
      def latest = (bin.listFiles.toList filter { _.getName startsWith "scala-2.10.0.r" } sortWith { _.getName > _.getName }).headOption
      latest match {
        case Some(scala) => {
          logger.info("found nightly build of scala compiler: %s" format scala.getName)
          scala
        }
        case None => {
          logger.info("downloading the latest scala compiler (nightly builds)")
          val u = url("http://www.scala-lang.org/archives/downloads/distrib/files/nightly/distributions/scala-2.10.0.latest.zip")
          val zip = file("/tmp/scala-latest.zip")
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
  
  lazy val project = Project(
    id = "aRDF",
    base = file("."),
    settings = buildSettings
  )


}

