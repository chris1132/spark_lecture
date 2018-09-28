package com.chovy.spark.mysql

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}

object WriteData2Mysql {


  def main(args:Array[String]):Unit= {
    val spark = SparkSession.builder()
      .appName("caseThird")
      .master("local")
      .getOrCreate()

    import spark.implicits._

    val context = spark.sparkContext
    context.setLogLevel("ERROR")


    val stuRDD = context.parallelize(Array("3 Rongcheng M 26", "4 wangchao F 16")).map(_.split(" "))


    val chemaString = "id name gender age"

//    val schema = StructType(chemaString.split(" ").map(e => StructField(e, StringType, nullable = true)))
    val schema = StructType(Array(
      StructField("id",IntegerType,nullable = true),
      StructField("name",StringType,nullable = true),
      StructField("gender",StringType,nullable = true),
      StructField("age",IntegerType,nullable = true)))

    val rowRDD = stuRDD.map(e => Row(e(0).toInt, e(1).trim, e(2).trim, e(3).toInt))

    val studentDF = spark.createDataFrame(rowRDD, schema)

    val connect = new MySqlConnect()

    studentDF.write.mode("append").jdbc(connect.url,"spark.student",connect.getProperties())

  }

}
