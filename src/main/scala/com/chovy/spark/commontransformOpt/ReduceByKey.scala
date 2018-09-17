package com.chovy.spark.commontransformOpt

import org.apache.spark.{SparkConf, SparkContext}

object ReduceByKey {
  def main(arg:Array[String])={

    val con = new SparkConf().setAppName("reduceByKey").setMaster("local")

    val sc = new SparkContext(con)


    val line = sc.textFile("hdfs://localhost:9000/wordcount/test.txt")


    val pairrdd = line.flatMap(e=>e.split(" ")).map((_,1))

    pairrdd.reduceByKey((a,b)=>a+b).foreach(println)

  }

}
