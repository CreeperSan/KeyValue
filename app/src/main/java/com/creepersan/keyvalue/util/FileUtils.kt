package com.creepersan.keyvalue.util

import android.os.Environment
import java.io.File

object FileUtils {

    private val ROOT_DIRECTORY = Environment.getExternalStorageDirectory()
    private val APP_DIRECTORY = File("${ROOT_DIRECTORY.absolutePath}/KeyValue")
    private val BACKUP_DIRECTORY = File("${APP_DIRECTORY.absolutePath}/backup")


    fun initFileFolder():Boolean{
        if (BACKUP_DIRECTORY.exists()){
            return BACKUP_DIRECTORY.isDirectory
        }else{
            return BACKUP_DIRECTORY.mkdirs()
        }
    }

}