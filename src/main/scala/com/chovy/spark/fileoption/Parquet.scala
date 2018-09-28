package com.chovy.spark.RDD.fileoption

import org.apache.spark.sql.SparkSession

object Parquet {

  def main(args:Array[String]):Unit={

    val spark = SparkSession.builder()
      .master("local")
      .appName("saveAsFile")
      .getOrCreate()

    import spark.implicits._

    val context = spark.sparkContext

    val parquetDF = spark.read.parquet("file:///usr/local/spark/examples/src/main/resources/users.parquet")

    parquetDF.createOrReplaceTempView("parquet")

    val nameDF = spark.sql("select * from parquet")

    nameDF.foreach(e=>println("name:"+e(0)+"|color:"+e(1)))


  }
}
