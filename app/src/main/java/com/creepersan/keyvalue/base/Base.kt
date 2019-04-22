package com.creepersan.keyvalue.base

import android.content.Context

fun Int.toString(context: Context):String{
    return context.getString(this)
}