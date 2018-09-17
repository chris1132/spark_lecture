package com.chovy.spark.sharedvariables

import org.apache.spark.{SparkConf, SparkContext}

object CaseOne {

  def main(args:Array[String]):Unit={


    val conf = new SparkConf().setAppName("caseOne").setMaster("local")

    val sc = new SparkContext(conf)
    //创建一个广播变量
    val broadcastVar = sc.broadcast(3)

    val listRdd = sc.parallelize(Array(1,2,3,4,5))

    val res = listRdd.map(e=>e*broadcastVar.value).foreach(println)

    println("---------------")

    val accum = sc.longAccumulator("my accumulator")

    sc.parallelize(Array(1,2,3,4)).foreach(x=>accum.add(x))

    println(accum.value)

    sc.stop()
  }
}
