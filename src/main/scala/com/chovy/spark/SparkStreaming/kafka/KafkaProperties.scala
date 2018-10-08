package com.chovy.spark.SparkStreaming.kafka

import java.util.Properties

import org.apache.kafka.clients.producer
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig}
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming.kafka010.KafkaUtils

import scala.collection.mutable

class KafkaProperties {

  val topics = "test"
  val brokers = "localhost:9092"
  val groupId = "spark_stream_chovy"

  @Deprecated
  def KafkaProducerInatialized():Map[String,Object]={
    val props = Map[String,Object](
      ProducerConfig.BOOTSTRAP_SERVERS_CONFIG->brokers,
      ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG->"org.apache.kafka.common.serialization.StringSerializer",
      ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG-> "org.apache.kafka.common.serialization.StringSerializer",
      "group.id" -> groupId,
      "auto.offset.reset" -> "latest",
      "enable.auto.commit"->(false:java.lang.Boolean)
    )
    props
  }

  def KafkaProducerInatialized2():Properties= {
    val param = new Properties
    param.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,brokers)
    param.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer")
    param.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer")
    param.put("group.id",groupId)
    param.put("auto.offset.reset","latest")
    param.put("enable.auto.commit",(false:java.lang.Boolean))
    param
  }

  def KafkaConsumerInatialized():Properties= {
    val param = new Properties
    param.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,brokers)
    param.put("key.deserializer",classOf[StringDeserializer])
    param.put("value.deserializer",classOf[StringDeserializer])
    param.put("group.id",groupId)
    param.put("auto.offset.reset","latest")
    param.put("enable.auto.commit",(false:java.lang.Boolean))
    param
  }

  def KafkaConsumerInatialized2():Map[String,Object]={
    val kafkaParams=Map[String,Object](
      ProducerConfig.BOOTSTRAP_SERVERS_CONFIG->brokers,
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> groupId,
      "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )
    kafkaParams
  }

}
