import sbt._

object Dependencies {

  val coreDependencies = Seq(
    "org.scala-lang" % "scala-library" % "2.13.9",
    "org.scala-lang" % "scala-reflect" % "2.13.9",
    "org.scala-lang.modules" %% "scala-collection-contrib" % "0.2.2",
    "org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.4",
    "org.scalatest" %% "scalatest" % "3.2.12" % "test",
    "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5",
    "com.typesafe.akka" %% "akka-actor" % "2.6.19",
    "com.typesafe.akka" %% "akka-slf4j" % "2.6.19",
    "com.typesafe.akka" %% "akka-stream" % "2.6.19",
    "com.typesafe.akka" %% "akka-cluster" % "2.6.19",
    "com.typesafe.akka" %% "akka-cluster-tools" % "2.6.19",
    "com.typesafe.akka" %% "akka-cluster-metrics" % "2.6.19",
    "com.typesafe.akka" %% "akka-stream-typed" % "2.6.19",
    "com.typesafe.akka" %% "akka-actor-typed" % "2.6.19",
    "com.typesafe.akka" %% "akka-cluster-typed" % "2.6.19",
    "com.typesafe.akka" %% "akka-cluster-sharding-typed" % "2.6.19",
    "com.typesafe.akka" %% "akka-stream-typed" % "2.6.19",
    "com.typesafe.akka" %% "akka-discovery" % "2.6.19",
    "com.lightbend.akka.management" %% "akka-management-cluster-bootstrap" % "1.1.3",
    "com.lightbend.akka.discovery" %% "akka-discovery-kubernetes-api" % "1.1.3",
    "com.typesafe.akka" %% "akka-stream-testkit" % "2.6.19",
    "com.typesafe.akka" %% "akka-stream-testkit" % "2.6.19" % "test",
    "com.typesafe.akka" %% "akka-http" % "10.2.9",
    "com.typesafe.akka" %% "akka-http-core" % "10.2.9",
    "com.typesafe.akka" %% "akka-http-testkit" % "10.2.9",
    "com.typesafe.akka" %% "akka-http-spray-json" % "10.2.9",
    "com.typesafe.akka" %% "akka-http-testkit" % "10.2.9" % "test",
    "de.heikoseeberger" %% "akka-http-json4s" % "1.39.2",
    "org.json4s" %% "json4s-native" % "4.0.6",
    "org.json4s" %% "json4s-core" % "4.0.6",
    "org.json4s" %% "json4s-ext" % "4.0.6",
    "org.json4s" %% "json4s-jackson" % "4.0.6",
    "com.couchbase.client" %% "scala-client" % "1.4.0",
    "org.apache.commons" % "commons-lang3" % "3.12.0",
    "com.github.t3hnar" %% "scala-bcrypt" % "4.3.0",
    "org.apache.logging.log4j" % "log4j-slf4j-impl" % "2.19.0"
  )

}
