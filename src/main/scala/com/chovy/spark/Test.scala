package com.chovy.spark

import org.apache.spark.{SparkConf, SparkContext}

object Test {

  def main(args:Array[String]):Unit={

    val conf = new SparkConf().setAppName("mySpark").setMaster("local")
    val sc = new SparkContext(conf)


    val data = sc.parallelize(1 to 10,5)

    data.map((_,1)).partitionBy(new UsridPartitioner(10))
      .map(_._1).saveAsTextFile("file:///usr/local/output")
  }

}
