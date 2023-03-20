import Dependencies._
import sbt.Tests.{Group, SubProcess}

lazy val commonSettings = Seq(
  scalaVersion := "2.13.9",
  organization := "hu.elte.inf",
  organizationName := "ELTE IK",
  scalacOptions ++= List(
    "-Yrangepos",
    "-encoding",
    "UTF-8",
    "-deprecation",
    "-feature",
    "-unchecked",
    "-Ywarn-unused",
    "-language:implicitConversions",
    "-language:postfixOps",
    "-Ymacro-annotations"
  ),
  fork := true,
  IntegrationTest / fork := true,
  Test / fork := true,
  Test / testForkedParallel := true,
  IntegrationTest / testForkedParallel := true,
  Global / concurrentRestrictions := Seq(Tags.limitAll(20)),
  Test / parallelExecution := true,
  Test / testGrouping := (Test / testGrouping).value.flatMap {
    group =>
      group.tests.map(test =>
        Group(
          test.name,
          Seq(test),
          /**
           * @note Any forked JVM parameter must be defined here. `javaOptions` will not work, because
           *       here we invoke the `SubProcess` that overrides `javaOptions`.
           *       This is needed for Spark to work with Java 17.
           */
          SubProcess(ForkOptions().withRunJVMOptions(scala.Vector(
            "--add-opens=java.base/java.nio=ALL-UNNAMED",
            "--add-opens=java.base/sun.nio.ch=ALL-UNNAMED",
            "--add-opens=java.base/java.util=ALL-UNNAMED",
            "--add-opens=java.base/java.lang=ALL-UNNAMED",
            "--add-opens=java.base/java.lang.invoke=ALL-UNNAMED",
            "-XX:ActiveProcessorCount=4",
            "-Dlog4j.configurationFile=common/src/main/resources/log4j2.properties",
            "-Xmx2g"
          )))
        )
      )
  },
  Test / logLevel := Level.Info,
  resolvers ++= Seq(
    "Maven Central".at("https://repo1.maven.org/maven2/")
  )
)

lazy val core = (project in file("core"))
  .settings(commonSettings: _*)
  .settings(
    name := "Core",
    description := "Core modules.",
    libraryDependencies ++= coreDependencies
  )

lazy val gatling = (project in file("gatling"))
  .settings(commonSettings: _*)
  .settings(
    name := "Gatling",
    description := "Stress tests."
  )
  .dependsOn(
    core % "test->test;compile->compile"
  )

lazy val wiki = (project in file("."))
  .settings(commonSettings: _*)
  .aggregate(core, gatling)