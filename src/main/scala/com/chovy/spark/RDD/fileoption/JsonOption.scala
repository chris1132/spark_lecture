package com.chovy.spark.RDD.fileoption

import org.apache.spark.{SparkConf, SparkContext}

import scala.util.parsing.json.JSON

object JsonOption {


  def main(args:Array[String]):Unit={

    val conf = new SparkConf().setAppName("jsonOption").setMaster("local")

    val sc = new SparkContext(conf)

    val jsonStr = sc.textFile("file:///usr/local/spark/examples/src/main/resources/employees.json")

//    jsonStr.foreach(println)

//    val jsonMap = JSON.parseFull(jsonStr.toString())

    val res = jsonStr.map(e=>JSON.parseFull(e))

    var index = -1
    res.foreach({
      r => r match {
        case Some(map:Map[String,Any]) =>for((k,v)<-map){println(k+":"+v)}
      }
    })


    sc.stop()

  }
}
