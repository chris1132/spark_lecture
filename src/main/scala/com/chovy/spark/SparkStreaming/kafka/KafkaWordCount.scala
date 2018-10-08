package com.chovy.spark.SparkStreaming.kafka

import org.apache.commons.codec.StringDecoder
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils}
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
object KafkaWordCount {


  def main(args:Array[String]):Unit={

    val spark = new SparkInatializer
    val ssc = spark.getStreamingContext("kafkaWordCount",5)

    ssc.checkpoint("file:///usr/local/spark/MyFile/kafka/checkpoint")

    val kafkaProperties = new KafkaProperties

    val messages=KafkaUtils.createDirectStream[String,String](
      ssc,
      PreferConsistent,
      Subscribe[String,String](kafkaProperties.topics.split(",").toSet[String],kafkaProperties.KafkaConsumerInatialized2())
    )

    val lines = messages.map(e=>e.value())

    val worldCount = lines.flatMap(e=>{
      e.split(" ").map((_,1))
    }).reduceByKey(_+_)

    worldCount.print()

    ssc.start()

    ssc.awaitTermination()
  }
}
