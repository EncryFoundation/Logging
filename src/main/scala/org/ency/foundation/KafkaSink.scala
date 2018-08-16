package org.ency.foundation

import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.apache.kafka.common.serialization.StringSerializer
import org.apache.spark.sql.ForeachWriter

class KafkaSink(topic:String, brokers:String) extends ForeachWriter[(String, String)]{
  val kafkaProperties = new Properties()
  kafkaProperties.put("bootstrap.servers", brokers)
  kafkaProperties.put("key.serializer", classOf[StringSerializer])
  kafkaProperties.put("value.serializer", classOf[StringSerializer])
  kafkaProperties.put("group.id", "encry")

  var producer: KafkaProducer[String, String] = _

  def open(partitionId: Long, version: Long): Boolean = {
    producer = new KafkaProducer(kafkaProperties)
    true
  }

  def process(value: (String,String)): Unit = {
    producer.send(new ProducerRecord(topic, value._1 + " : " + value._2))
  }

  def close(errorOrNull: Throwable): Unit = {
    producer.close()
  }
}
