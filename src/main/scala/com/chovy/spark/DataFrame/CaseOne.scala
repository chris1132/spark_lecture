package com.chovy.spark.DataFrame

import org.apache.spark.api.java.JavaSparkContext
import org.apache.spark.sql.SparkSession

object CaseOne {


  def main(args:Array[String]):Unit ={



    val spark = SparkSession.builder()
      .appName("caseOne")

      .master("local").getOrCreate()


    val conf = new JavaSparkContext(spark.sparkContext)

    conf.setLogLevel("ERROR")

    import spark.implicits._

    val df = spark.read.json("file:///usr/local/spark/examples/src/main/resources/people.json")

    df.show()

    df.printSchema()

    df.select(df("name"),df("age")+1).show()

    df.filter(df("age")>20).show()

    df.groupBy("age").count().show()

    df.sort(df("age").desc).show()

    df.sort(df("age").desc,df("name").asc).show()

    df.select(df("name").as("userName"),df("age")).show()

  }

}
