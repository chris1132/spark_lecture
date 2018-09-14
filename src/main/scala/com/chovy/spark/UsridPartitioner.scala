package com.chovy.spark


import org.apache.spark.{Partitioner,SparkContext,SparkConf}
class UsridPartitioner(numParts:Int) extends Partitioner {

  override def numPartitions: Int = numParts

  override def getPartition(key: Any): Int = {
    key.toString.toInt%10
  }

}
