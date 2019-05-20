package com.creepersan.keyvalue.util

import android.os.Environment
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.creepersan.keyvalue.database.KeyValue
import com.creepersan.keyvalue.database.Table
import java.io.File
import java.lang.Exception
import java.util.ArrayList
import java.util.HashMap

/**
 *
 ***************************************************************************************************
 *
 *  文件格式如下
 *
 *  HEADER (5 byte)     头部标志
 *  EXT_COUNT (1 byte)  拓展信息数量
 *       |- EXT_TYPE (1 byte)       拓展信息类型
 *       |- EXT_LENGTH (2 byte)     拓展信息长度
 *       |- EXT_VALUE (1 byte)      拓展信息值
 *  CONTENT_LENGTH (4 byte)         内容长度
 *  CONTENT (x byte)                内容(如果有密码，则使用AES-128加密)
 *  FOOTER (5 byte)     尾部标志
 *
 *
 ***************************************************************************************************
 *
 *  拓展信息标志如下
 *
 *  0x01    密码，如果为值为0则没有密码，为1则有密码
 *
 *
 */

object FileUtils {

    private val ROOT_DIRECTORY = Environment.getExternalStorageDirectory()
    private val APP_DIRECTORY = File("${ROOT_DIRECTORY.absolutePath}/KeyValue")
    private val BACKUP_DIRECTORY = File("${APP_DIRECTORY.absolutePath}/backup")

    private val POSTFIX = "kvb"

    val BACKUP_EXT_VERSION = byte(0x01)
    val BACKUP_EXT_VERSION_01 = byteArrayOf(byte(0x01))
    private val BACKUP_HEADER = byteArrayOf(byte(0x06), byte(0x98), byte(0x45), byte(0xFF), byte(0xCC))
    private val BACKUP_FOOTER = byteArrayOf(byte(0xC9), byte(0x95), byte(0x27), byte(0x00), byte(0xAA))
    private const val BACKUP_JSON_ROOT_TABLE = "table"
    private const val BACKUP_JSON_ROOT_KEY_VALUE = "key_value"
    private val BACKUP_CHARSET = Charsets.UTF_8




    fun initFileFolder():Boolean{
        if (BACKUP_DIRECTORY.exists()){
            return BACKUP_DIRECTORY.isDirectory
        }else{
            return BACKUP_DIRECTORY.mkdirs()
        }
    }

    fun isBackupFileExist(tmpFileName:String):Boolean{
        val fileName = "${tmpFileName.trim()}.$POSTFIX"
        if (fileName.isEmpty()){
            return true
        }
        if (fileName.contains("/")){
            return true
        }
        BACKUP_DIRECTORY.listFiles().forEach { tmpFile ->
            if (tmpFile.name == fileName){
                return true
            }
        }
        return false
    }

    fun getBackupFileNamelist():ArrayList<String>{
        val nameList = ArrayList<String>()
        BACKUP_DIRECTORY.listFiles().forEach { file ->
            val fileName = file.name
            val postfix = ".$POSTFIX"
            if (fileName.endsWith(postfix)){
                nameList.add(fileName.substring(0, fileName.lastIndexOf(postfix)))
            }
        }
        return nameList
    }

    fun writeBackupFile(fileName:String, extMap:HashMap<Byte, ByteArray>, tableList:List<Table>, keyValueList:List<KeyValue>, resultCallback:((result:Boolean, hint:String)->Unit)?=null, stepCallback:((hint:String)->Unit)?=null){
        val file = File("${BACKUP_DIRECTORY.absolutePath}/$fileName.$POSTFIX")
        if (file.exists()){
            resultCallback?.invoke(false, "文件已经存在，无法创建文件")
            return
        }
        Thread{
            try {
                stepCallback?.invoke("正在创建文件")
                val outSteam = file.outputStream()
                // 写Header
                stepCallback?.invoke("正在写入标志")
                outSteam.write(BACKUP_HEADER)
                // 写EXT
                stepCallback?.invoke("正在写入备份信息")
                outSteam.write(extMap.size)
                extMap.keys.forEach {  key ->
                    val value = extMap[key]!!
                    outSteam.write(byteArrayOf(key))
                    outSteam.write(value.size.to2Byte())
                    outSteam.write(value)
                }
                // 计算内容
                stepCallback?.invoke("正在压缩备份文件")
                val json = JSONObject()
                val jsonTable = JSONArray()
                tableList.forEach { table ->
                    jsonTable.add(table.toJsonObject())
                }
                json.put(BACKUP_JSON_ROOT_TABLE, jsonTable)
                val keyValueJson = JSONArray()
                keyValueList.forEach {  keyValue ->
                    keyValueJson.add(keyValue)
                }
                json.put(BACKUP_JSON_ROOT_KEY_VALUE, keyValueJson)
                // 写内容长度
                stepCallback?.invoke("正在写入备份文件")
                val jsonString = json.toJSONString()
                outSteam.write(jsonString.length.to4Byte())
                // 写内容
                outSteam.write(jsonString.toByteArray(BACKUP_CHARSET))
                // 写Footer
                outSteam.write(BACKUP_FOOTER)
                outSteam.flush()
                outSteam.close()
                resultCallback?.invoke(true, "写入备份文件成功!")
            }catch (e:Exception){
                e.printStackTrace()
                resultCallback?.invoke(false, "备份失败")
            }
        }.start()
    }



    fun readBackupFile(fileName:String){

    }

}