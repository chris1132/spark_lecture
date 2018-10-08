package com.chovy.spark.SparkStreaming.kafka

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils}
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe

import scala.util.Random

object KafkaWordProducer {

  val messagesPerSec=1 //每秒发送几条信息
  val wordsPerMessage =4 //一条信息包括多少个单词

  def main(args:Array[String]):Unit={
    val spark = new SparkInatializer
    val ssc = spark.getSparkContext("kafkaWordProducer",5)

    val kafkaProperties = new KafkaProperties

    val producer = new KafkaProducer[String,Object](kafkaProperties.KafkaProducerInatialized2())

    while(true){
      (1 to messagesPerSec).foreach(e=>{
        val str = (1 to wordsPerMessage).map(x=>Random.nextInt(10).toString).mkString(" ")
        val message = new ProducerRecord[String,Object](kafkaProperties.topics,null,str)

        producer.send(message)
        println(message)
      })

      Thread.sleep(1000)

    }


    //TODO
//
//    val messages = KafkaUtils.createDirectStream[String,String](
//      ssc,PreferConsistent, ConsumerStrategies.Subscribe[String,String](kafkaProperties.topic,kafkaProperties.KafkaProducerInatialized())
//    )
//
//    val lines=messages.map(x=>{
//      x.value()
//    })
//    val wordCounts=lines.flatMap(x=>{
//      x.split(" ").map(x=>(x,1))
//    }).reduceByKey(_+_)
//    wordCounts.print()
//    ssc.start()
//    ssc.awaitTermination()

  }
}
