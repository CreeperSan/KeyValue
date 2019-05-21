package com.creepersan.keyvalue.util

object FormatCheckUtil {

    fun checkEditText(data:String, maxLength:Int = Int.MAX_VALUE, minLength:Int = 0):Boolean{
        val length = data.length
        if (length > maxLength){ return false }
        if (length < minLength){ return false }
        return true
    }

}