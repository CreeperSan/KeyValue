package com.creepersan.keyvalue.database

class Table(var id:Int, var name:String, var type:Int, var icon:Int, var createTime:Long, var description:String)
class KeyValuePair(var id:Int, var name:String, var type:Int, var icon:Int, var value:String, var table:Int, var createTime: Long, var description:String)