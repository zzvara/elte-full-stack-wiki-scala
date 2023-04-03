logLevel := Level.Warn

addDependencyTreePlugin
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.9.3")
addSbtPlugin("com.github.sbt" % "sbt-release" % "1.1.0")
addSbtPlugin("ch.epfl.scala" % "sbt-scalafix" % "0.10.3")
addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "2.1.1")
addSbtPlugin("com.typesafe.sbt" % "sbt-license-report" % "1.2.0")
