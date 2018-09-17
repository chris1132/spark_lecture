package com.chovy.spark.commontransformOpt

import org.apache.spark.Partitioner
class UsridPartitioner(numParts:Int) extends Partitioner {

  override def numPartitions: Int = numParts

  override def getPartition(key: Any): Int = {
    key.toString.toInt%10
  }

}
