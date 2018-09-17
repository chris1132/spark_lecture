package com.chovy.spark.commontransformOpt

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

//求平均值
object CaseOne {

  def main(args:Array[String]):Unit = {

    val conf = new SparkConf().setAppName("caseOne").setMaster("local")

    val sc = new SparkContext(conf)

    val data = Array(("hadoop",4),("spark",4),("hadoop",6),("spark",2),("hadoop",11))

    val rdd = sc.parallelize(data)

    val rdd2 = rdd.mapValues(x=>(x,1)).reduceByKey((a,b)=>(a._1+b._1,a._2+b._2))
        .mapValues(x=>(x._1/x._2)).collect()

    rdd2.foreach(println)

  }

}
