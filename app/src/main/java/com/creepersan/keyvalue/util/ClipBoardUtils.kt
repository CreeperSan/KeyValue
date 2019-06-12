package com.creepersan.keyvalue.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

object ClipBoardUtils {

    fun copyText(context: Context, content:String){
        val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboardManager.primaryClip = ClipData.newPlainText("KeyValue", content)
    }

}