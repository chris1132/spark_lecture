package com.chovy.spark

import org.apache.spark.{SparkConf, SparkContext}

object GroupByKey {


  def main(arg:Array[String]):Unit={

    val conf = new SparkConf().setAppName("groupByKey").setMaster("local")

    val sc = new SparkContext(conf)

    val lines = sc.textFile("hdfs://localhost:9000/wordcount/text.txt")

    val rdd = lines.flatMap(e=>e.split(" ")).map((_,1))

    rdd.groupByKey().foreach(println)

  }

}
