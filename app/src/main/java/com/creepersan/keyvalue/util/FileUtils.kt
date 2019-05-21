package com.creepersan.keyvalue.util

import android.os.Environment
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONException
import com.alibaba.fastjson.JSONObject
import com.creepersan.keyvalue.database.KeyValue
import com.creepersan.keyvalue.database.Table
import java.io.File
import java.io.InputStream
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
                    keyValueJson.add(keyValue.toJsonObject())
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



    fun readBackupFile(fileName:String, resultCallback:((result:Boolean, reason:String)->Unit), dataCallback:(tableList:ArrayList<Table>, keyValueList:ArrayList<KeyValue>)->Unit,stepCallback:((hint:String)->Unit)?=null){
        val file = File("${BACKUP_DIRECTORY.absolutePath}/$fileName.$POSTFIX")
        if (!file.exists()){
            resultCallback.invoke(false, "备份文件不存在")
        }
        Thread{
            var inSteam : InputStream? = null
            try {
                inSteam = file.inputStream()
                stepCallback?.invoke("正在检查文件头部信息")
                if (!inSteam.readByteArray(BACKUP_HEADER.size).isSame(BACKUP_HEADER)){
                    resultCallback.invoke(false, "文件不合法，格式对比不通过")
                    return@Thread
                }
                stepCallback?.invoke("正在检查文件拓展信息")
                val extCount = inSteam.readByteArray(1)[0]
                if (extCount < 0){
                    resultCallback.invoke(false, "文件不合法，拓展信息数量错误")
                    return@Thread
                }else if (extCount > 0){
                    for (i in 0 until extCount){
                        val extType = inSteam.read()
                        val lengthByteArray = inSteam.readByteArray(2)
                        val extLength = (lengthByteArray[1].toUInt() + lengthByteArray[0].toUInt()*256.toUInt()).toInt()
                        val extData = inSteam.readByteArray(extLength)
                    }
                }
                stepCallback?.invoke("正在检查文件总长信息")
                val contentLength = inSteam.readByteArray(4).toInt()
                if (contentLength < 0){
                    resultCallback.invoke(false, "文件不合法，数据长度错误")
                    return@Thread
                }
                stepCallback?.invoke("正在检查文件内容")
                val data = inSteam.readByteArray(contentLength).toString(BACKUP_CHARSET)
                val keyValueList = ArrayList<KeyValue>()
                val tableList = ArrayList<Table>()
                try {
                    stepCallback?.invoke("正在校验文件内容")
                    val json = JSONObject.parseObject(data)
                    val tableJson = json.getJSONArray(BACKUP_JSON_ROOT_TABLE)
                    tableJson.forEach {  tmpTableJson ->
                        val tableJsonObject = tmpTableJson as JSONObject
                        tableList.add(Table.fromJsonObject(tableJsonObject))
                    }
                    val keyValueJson = json.getJSONArray(BACKUP_JSON_ROOT_KEY_VALUE)
                    keyValueJson.forEach {  tmpKeyValueJson ->
                        val keyValueJsonObject = tmpKeyValueJson as JSONObject
                        keyValueList.add(KeyValue.fromJsonObject(keyValueJsonObject))
                    }
                }catch (e:JSONException){
                    e.printStackTrace()
                    resultCallback.invoke(false, "数据解析出错")
                }
                stepCallback?.invoke("正在完成校验")
                if (!inSteam.readByteArray(5).isSame(BACKUP_FOOTER)){
                    resultCallback.invoke(false, "文件不合法，格式对比失败，文件可能已经损坏")
                    return@Thread
                }
                stepCallback?.invoke("读取完毕")
                dataCallback.invoke(tableList, keyValueList)
                resultCallback.invoke(true, "已恢复备份")
            }catch (e:Exception){
                resultCallback.invoke(false, "恢复备份出错")
                e.printStackTrace()
            }finally {
                inSteam?.close()
            }
        }.start()
    }

}