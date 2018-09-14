package com.chovy.spark

import org.apache.spark.{SparkConf, SparkContext}

object CompareGroupKAndReduceK {


  def main(arg:Array[String])={
    val conf = new SparkConf().setAppName("compare").setMaster("local")

    val sc = new SparkContext(conf)

    val words = Array("one","two","three","four","one")

    val wordRDD = sc.parallelize(words).map((_,2))

    val wordReduce = wordRDD.reduceByKey(_+_).foreach(println)

    println("1------------------------------")

    val wordGroup = wordRDD.groupByKey().map(e=>(e._1,e._2.sum)).foreach(println)

    println("2------------------------------")

    val wordSort = wordRDD.reduceByKey((a,b)=>a+1).sortByKey(false).foreach(println)

    println("3------------------------------")

    val wordSortByKey = wordRDD.reduceByKey(_+_).sortBy(_._2,false).foreach(println)

    println("4------------------------------")

    val mapValue = wordRDD.mapValues(x=>x+2).foreach(println)

  }


}
