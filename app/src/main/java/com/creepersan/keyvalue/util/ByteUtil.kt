package com.creepersan.keyvalue.util

import java.io.InputStream
import java.nio.ByteBuffer

fun byte(int:Int):Byte{
    return int.toByte()
}

fun Int.to2Byte():ByteArray{
    return this.toByteArray(2)
}

fun Int.to3Byte():ByteArray{
    return this.toByteArray(3)
}

fun Int.to4Byte():ByteArray{
    return this.toByteArray(4)
}

fun Long.to5Byte():ByteArray{
    return this.toByteArray(5)
}

fun Long.to6Byte():ByteArray{
    return this.toByteArray(6)
}

fun Long.to7Byte():ByteArray{
    return this.toByteArray(7)
}

fun Long.to8Byte():ByteArray{
    return this.toByteArray(8)
}

fun ByteArray.toInt():Int{
    return ByteBuffer
            .wrap(this)
            .int
}

fun ByteArray.toLong():Long{
    return ByteBuffer.wrap(this).long
}

fun ByteArray.isSame(target:ByteArray):Boolean{
    if (this.size != target.size){
        return false
    }
    this.forEachIndexed { index, byte ->
        if (byte != target[index]){
            return false
        }
    }
    return true
}

/**
 * 从FileInputSteam中读取指定长度的字节数
 */

fun InputStream.readByteArray(length:Int):ByteArray{
    val byteArray = ByteArray(length)
    this.read(byteArray)
    return byteArray
}

/**
 * 基础方法
 */

private fun Number.toByteArray(size:Int):ByteArray{
    val array = ByteArray(size){0}
    for (i in 0 until array.size){
        array[size-1-i] = ((this.toLong() shr i*8) and 0xFF).toByte()
    }
    return array
}