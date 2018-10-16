package com.chovy.spark.SparkStreaming

import java.sql.{Connection, DriverManager, PreparedStatement}

import com.chovy.spark.SparkStreaming.kafka.SparkInatializer
import org.apache.spark.sql.Row
import org.apache.spark.sql.types.StructType
import org.apache.spark.storage.StorageLevel
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}


object NetworkWordCountStateful {

  def main(args:Array[String]):Unit={
    val updateFunc = (values:Seq[Int],state:Option[Int])=>{
      val currentCount = values.foldLeft(0)(_+_)
      val previousCount = state.getOrElse(0)
      Some(currentCount+previousCount)
    }

    val spark = new SparkInatializer()

    val sc = spark.getStreamingContext("networkWordCountStateful",5)

    sc.checkpoint("file:///usr/local/spark/MyFile/kafka/checkpoint")

    sc.sparkContext.setLogLevel("ERROR")

    val lines = sc.socketTextStream("localhost",9999,StorageLevel.MEMORY_AND_DISK_SER)

    val words =lines.flatMap(e=>e.split(" "))

    val wordDStream = words.map((_,1))

    val stateDStream = wordDStream.updateStateByKey[Int](updateFunc)

//    stateDStream.print()

   // stateDStream.saveAsTextFiles("file:///usr/local/spark/MyFile/wordCount.txt")



    stateDStream.foreachRDD(rdd=> {

      def func(records: Iterator[(String, Int)])={
        var conn: Connection = null
        var stmt: PreparedStatement = null
        var delstmt: PreparedStatement = null
        try {
          val url = "jdbc:mysql://127.0.0.1:3306/spark"
          val user = "root"
          val password = "wch1132"
          conn = DriverManager.getConnection(url, user, password)

          records.foreach(e => {
            val sql ="insert into wordcount(word,count) values(?,?)"
            stmt = conn.prepareStatement(sql)
            stmt.setString(1, e._1.trim)
            stmt.setInt(2, e._2.toInt)
            stmt.executeUpdate()
          })

        } catch {
          case e: Exception => e.printStackTrace()
        } finally {
          if (stmt != null) {
            stmt.close()
          }
          if (conn != null) {
            conn.close()
          }
        }
      }
      val repartitionRDD = rdd.repartition(3)
      repartitionRDD.foreachPartition(func)
    })

    sc.start()

    sc.awaitTermination()






  }
}
