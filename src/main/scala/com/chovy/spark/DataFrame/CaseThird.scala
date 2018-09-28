package com.chovy.spark.DataFrame

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types._

/**
  * 编程方式定义RDD
  * 当无法提前定义case class时，就需要采用编程方式定义RDD模式
  * */
object CaseThird {

  def main(args:Array[String]):Unit = {

    val spark = SparkSession.builder()
      .appName("caseThird")
      .master("local")
      .getOrCreate()

    import spark.implicits._

    val context = spark.sparkContext
    context.setLogLevel("ERROR")

    val peopleRDD = context.textFile("file:///usr/local/spark/examples/src/main/resources/people.txt")

    //定义一个模式字符串
    val schemaString = "name,age"

    //调用split后，成为Array("name","age")
    //.map(e=>StructField(e)) @returen Array(StructField(name,StringType,true),StructField(age,StringType,true))
    val fields = schemaString.split(",").map(e=>StructField(e,StringType,nullable=true))

    //schema描述了模式信息，模式中包含name，age字段
    val schema = StructType(fields)
    //模式生成结束

    val rowRDD = peopleRDD.map((_.split(","))).map(e=>Row(e(0),e(1).trim))

    val peopleDF = spark.createDataFrame(rowRDD,schema)
      .createOrReplaceTempView("people")

    val results = spark.sql("select name,age from people")

    results.map(e=>"name:"+e(0)+",age:"+e(1)).show()
  }

}
