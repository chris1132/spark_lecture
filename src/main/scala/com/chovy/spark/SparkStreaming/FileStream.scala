package com.chovy.spark.SparkStreaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object FileStream {

  def main(args:Array[String]):Unit={

    val conf = new SparkConf()
      .setAppName("caseONe")
      .setMaster("local[2]")//设置2个线程，一个监听，另一个处理数据

    val ssc = new StreamingContext(conf,Seconds(20))

    ssc.sparkContext.setLogLevel("Error")

    val lines = ssc.textFileStream("file:///usr/local/spark/MyFile/streaming/logfile")

    val words = lines.flatMap(_.split(" "))

    val wordCount = words.map((_,1)).reduceByKey(_+_)

    wordCount.print()

    ssc.start()

    ssc.awaitTermination()
  }

}
