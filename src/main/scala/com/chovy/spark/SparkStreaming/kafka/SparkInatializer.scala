package com.chovy.spark.SparkStreaming.kafka

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.{Seconds, StreamingContext}

class SparkInatializer {


  def getSparkConf(appName:String):SparkConf={
    val sparkConf = new SparkConf()
      .setMaster("local[2]")
      .setAppName(appName)
    sparkConf
  }

  def getSparkContext(appName:String,time:Int): SparkContext ={
    val conf = getSparkConf(appName)
    val sc = new SparkContext(conf)
    sc
  }


  def getStreamingContext(appName:String,time:Int): StreamingContext ={
    val conf = getSparkConf(appName)
    val ssc = new StreamingContext(conf,Seconds(time))
    ssc
  }


  def getSparkSession(appName:String):SparkSession={
    val spark = SparkSession.builder()
      .master("local[2]")
      .appName(appName)
      .getOrCreate()

    spark
  }
}
