package com.chovy.spark.mysql

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types.{StringType, StructField, StructType}

object GetDataFromMysql {

  val spark = SparkSession.builder()
    .appName("caseThird")
    .master("local")
    .getOrCreate()

  import spark.implicits._

  val context = spark.sparkContext
  context.setLogLevel("ERROR")


  val stuRDD = context.parallelize(Array("3 Rongcheng M 26","4 wangchao F 16")).map(_.split(" "))


  val chemaString = "id name gender age"

  val schema = StructType(chemaString.split(" ").map(e=>StructField(e,StringType,nullable = true)))

  val rowRDD = stuRDD.map(e=>Row(e(0),e(1),e(2),e(3)))

}
