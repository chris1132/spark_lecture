package com.chovy.spark

import org.apache.spark.{SparkConf, SparkContext}

object CompareGroupKAndReduceK {


  def main(arg:Array[String])={
    val conf = new SparkConf().setAppName("compare").setMaster("local")

    val sc = new SparkContext(conf)

    val words = Array("one","two","three","four","fize")

    val wordRDD = sc.parallelize(words).map((_,1))

    val wordReduce = wordRDD.reduceByKey(_+_).foreach(println)

    println("------------------------------")

    val wordGroup = wordRDD.groupByKey().map(e=>(e._1,e._2.sum)).foreach(println)

  }


}
