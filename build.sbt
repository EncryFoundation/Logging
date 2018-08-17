name := "LoggingCenral"

version := "0.1"

scalaVersion := "2.11.12"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-sql" % "2.3.1",
  "org.apache.spark" % "spark-sql-kafka-0-10_2.11" % "2.3.1",
  "org.apache.spark" %% "spark-core" % "2.3.1",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test",
  "org.apache.kafka" % "kafka-clients" % "2.0.0")

assemblyJarName in assembly := "LoggingCentral.jar"

mainClass in assembly := Some("org.ency.foundation.LoggingCenralApp")

test in assembly := {}

assemblyMergeStrategy in assembly := {
  case "META-INF/MANIFEST.MF" => MergeStrategy.discard
  case "META-INF/BCKEY.SF" => MergeStrategy.discard
  case "META-INF/DEV.SF" => MergeStrategy.discard
  case "META-INF/DUMMY.SF" => MergeStrategy.discard
  case PathList("META-INF", xs@_*) ⇒ MergeStrategy.concat
  case _ => MergeStrategy.first
}