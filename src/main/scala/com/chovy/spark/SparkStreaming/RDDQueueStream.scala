package com.chovy.spark.SparkStreaming

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.{Seconds, StreamingContext}

import scala.collection.mutable

object RDDQueueStream {

  def main(args:Array[String]):Unit={

    val conf = new SparkConf().setMaster("local[2]")
      .setAppName("rddStream")

    val ssc = new StreamingContext(conf,Seconds(1))

    ssc.sparkContext.setLogLevel("ERROR")

    val rddQueue = new mutable.SynchronizedQueue[RDD[Int]]()

    val queueStream = ssc.queueStream(rddQueue)

    val res = queueStream.map(e=>(e%10,1)).reduceByKey(_+_)

    res.print()
    ssc.start()

    for (i<- 1 to 10){
      rddQueue += ssc.sparkContext.makeRDD(1 to 100,2)
      Thread.sleep(1000)
    }
    ssc.stop()
  }

}
