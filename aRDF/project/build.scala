import sbt._
import Keys._

object BuildSettings {

  val logger = ConsoleLogger()

  val buildSettings = Defaults.defaultSettings ++ Seq (
    organization := "org.w3",
    version      := "0.1",
    scalaVersion := "2.10.0-M1",
    parallelExecution in Test := false,
    scalacOptions ++= Seq("-deprecation", "-unchecked", "-Yvirtpatmat", "-optimize")
  )

}

object YourProjectBuild extends Build {

  import BuildSettings._
  
  val mySettings = Seq(
    resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
    resolvers += "apache-repo-releases" at "http://repository.apache.org/content/repositories/releases/",
//    resolvers += Resolver.url("Play", url("http://download.playframework.org/ivy-releases/"))(Resolver.ivyStylePatterns),
    libraryDependencies += "org.apache.jena" % "jena-arq" % "2.9.0-incubating",
    libraryDependencies += "com.novocode" % "junit-interface" % "0.8" % "test",
//    libraryDependencies += "com.typesafe" %% "play-mini" % "2.0-RC1-SNAPSHOT",
//    mainClass in (Compile, run) := Some("play.core.server.NettyServer")
    libraryDependencies += "com.typesafe.akka" % "akka-actor" % "2.0-M3"
  )
  
  lazy val project = Project(
    id = "aRDF",
    base = file("."),
    settings = buildSettings ++ mySettings
  )


}

