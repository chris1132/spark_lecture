package com.chovy.spark.DataFrame

import org.apache.spark.sql.{Encoder, SparkSession}
object CaseSecond {

  case class Person(name:String,age:Long)

  def main(args:Array[String]):Unit={

    val spark = SparkSession.builder()
      .master("local")
      .appName("caseSecond").getOrCreate()

    val conf = spark.sparkContext

    conf.setLogLevel("ERROR")

    import spark.implicits._

    val peopleDF = conf.textFile("file:///usr/local/spark/examples/src/main/resources/people.txt")
      .map(_.split(",")).map(e=>Person(e(0),e(1).trim.toInt)).toDF()

    peopleDF.show()

    peopleDF.createOrReplaceTempView("people")//必须注册为临时表才能供下面的查询使用

    val peopleRDD = spark.sql("select name,age from people where age>20")//最终生成一个DataFrame


    peopleRDD.map(e=>"Name:"+e(0)+",Age:"+e(1)).show()


  }
}
