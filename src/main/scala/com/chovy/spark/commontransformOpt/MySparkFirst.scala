package com.chovy.spark.commontransformOpt

import org.apache.spark.{SparkConf, SparkContext}

object MySparkFirst {
  def main(args:Array[String]):Unit={

    val conf = new SparkConf().setAppName("mySparkFirst")

    //setMaster("local") 本机的spark就用local，远端的就写ip
    //如果是打成jar包运行则需要去掉 setMaster("local")因为在参数中会指定。
    //conf.setMaster("local")

    val sc = new SparkContext(conf)
    val rdd = sc.parallelize(List(1,2,3,4,5)).map(_*3)
    val mapedRDD = rdd.filter(e=>e>10).collect()

    println(rdd.reduce(_+_))
    for(arg<- mapedRDD)
      println(arg + " ")

    println("success!!!")
  }

}
