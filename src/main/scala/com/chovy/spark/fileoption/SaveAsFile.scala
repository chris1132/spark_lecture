package com.chovy.spark.fileoption

import org.apache.spark.sql.SparkSession

object SaveAsFile {

  def main(args:Array[String]):Unit={

    val spark = SparkSession.builder()
      .master("local")
      .appName("saveAsFile")
      .getOrCreate()

    val context = spark.sparkContext

    val peopleDF = spark.read.json("file:///usr/local/spark/examples/src/main/resources/people.json")

    /**
      * 第一种方法： write.format 支持json，parquet，jdbc，orc，libsvm，csv等等
      * */
   // peopleDF.select("name","age").write.format("csv").save("file:///usr/local/spark/MyFile/newpeople.csv")


    /**
      * 第2种方法：
      * */
    peopleDF.rdd.saveAsTextFile("file:///usr/local/spark/MyFile/newpeople.txt")
  }

}
