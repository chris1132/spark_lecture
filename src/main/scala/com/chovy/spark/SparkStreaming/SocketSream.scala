package com.chovy.spark.SparkStreaming

import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.{Seconds, StreamingContext}


/**
  * 1、终端使用nc -lk 9999
  * 2、启动程序
  * 3、输入内容，下面控制台自动打印统计数据,只捕捉单次输入，无历史数据
  * */
object SocketSream {

  def main(args:Array[String]):Unit = {

    //StreamingExamples.setStreamingLogLevels()

    val conf = new SparkConf().setMaster("local[2]").setAppName("socketStream")

    val ssc = new StreamingContext(conf,Seconds(5))

    ssc.sparkContext.setLogLevel("ERROR")

    val lines = ssc.socketTextStream("localhost",9999,StorageLevel.MEMORY_AND_DISK_SER)

    val words = lines.flatMap(_.split(" "))

    val wordCount = words.map((_,1)).reduceByKey(_+_)
    wordCount.print()

    ssc.start()
    ssc.awaitTermination()

  }
}
