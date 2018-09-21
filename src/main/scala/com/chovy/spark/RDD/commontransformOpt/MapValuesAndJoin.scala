package com.chovy.spark.RDD.commontransformOpt

import org.apache.spark.{SparkConf, SparkContext}

object MapValuesAndJoin {

  def main(args:Array[String]):Unit={

    val conf = new SparkConf().setAppName("mapValuesAndJoin").setMaster("local")

    val sc = new SparkContext(conf)

    val array = Array("hadoop","spark","hive","hbase")

    val lines = sc.parallelize(array)

    val rdd = lines.map((_,1))

    rdd.mapValues(x=>x+1).foreach(println)

    println("----------------")


    val rdd2 = sc.parallelize(Array(("redis",3)))

    rdd.join(rdd2).foreach(println)

  }

}
