package com.chovy.spark

import org.apache.spark.{SparkConf, SparkContext}

object PairRdd {


  def main(arg:Array[String]):Unit={

    val conf = new SparkConf().setAppName("pairRdd").setMaster("local")


    val sc = new SparkContext(conf)


//    val lines = sc.textFile("file:///usr/local/word.txt")

    val line = sc.textFile("hdfs://localhost:9000/wordcount/test.txt")


    val pairrdd = line.flatMap(e=>e.split("")).map((_,1))

    pairrdd.foreach(println)
  }


}
