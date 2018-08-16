name := "LoggingCenral"

version := "0.1"

scalaVersion := "2.11.12"

libraryDependencies ++= Seq(
  "org.apache.spark"  %% "spark-sql" % "2.3.1",
  "org.apache.spark"  % "spark-sql-kafka-0-10_2.11" % "2.3.1",
  "org.apache.spark"  %% "spark-core" % "2.3.1",
  "org.scalatest"     %% "scalatest" % "3.0.5"  % "test",
  "org.apache.kafka" % "kafka-clients" % "2.0.0")