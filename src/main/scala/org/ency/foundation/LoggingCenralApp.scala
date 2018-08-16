package org.ency.foundation

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.streaming.Trigger
import org.apache.spark.sql.types._

object LoggingCenralApp extends App {

  val sparkSession = SparkSession
    .builder
    .appName("SPARK-Logging-Processor")
    .master("local")
    .getOrCreate()

  sparkSession.sparkContext.setLogLevel("ERROR")

  import sparkSession.implicits._

  val kafkaBrokers = "172.16.10.55:9092"

  val logFrame = sparkSession.
    readStream.format("kafka")
    .option("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    .option("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    .option("kafka.bootstrap.servers", kafkaBrokers)
    .option("subscribe", "Logs")
    .option("startingOffsets", "earliest")
    .option("group.id", "encry")
    .load()

  val transformedLogFrame = logFrame
    .withColumn("Key", $"key".cast(StringType))
    .withColumn("Topic", $"topic".cast(StringType))
    .withColumn("Offset", $"offset".cast(LongType))
    .withColumn("Partition", $"partition".cast(IntegerType))
    .withColumn("Timestamp", $"timestamp".cast(TimestampType))
    .withColumn("Value", $"value".cast(StringType))
    .select("Key", "Value", "Partition", "Offset", "Timestamp")

  transformedLogFrame.select("Partition", "Value", "Timestamp")
    .writeStream
    .format("console")
    .trigger(Trigger.ProcessingTime("3 seconds"))
    .start()
    .awaitTermination()
}
