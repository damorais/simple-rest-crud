name := """simple-rest-crud"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  //When you are using Slick-Play, the dependency of JDBC must be removed. Otherwise, you'll get a Dependency Injection
  // caused by a conflict of DB drivers
  //  jdbc,
  cache,
  ws,
  "com.typesafe.play" %% "play-slick" % "1.0.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "1.0.0",
  "org.postgresql" % "postgresql" % "9.4-1201-jdbc41",
  specs2 % Test
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
