package com.chovy.spark.mysql

import java.util.Properties

class MySqlConnect {

  val url = "jdbc:mysql://127.0.0.1:3306/spark"

  val user = "root"

  val password = "wch1132"

  def getProperties(): Properties={
    val prop = new Properties()
    prop.put("user",user)
    prop.put("password",password)
//    prop.put("useSSL",false)
    prop.put("characterEncoding","gbk")
//    prop.put("useUnicode",true)
    prop.put("driver","com.mysql.jdbc.Driver")
    prop
  }
}
