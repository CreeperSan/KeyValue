package com.creepersan.keyvalue.util

import android.util.Base64
import com.creepersan.keyvalue.database.KeyValue
import com.creepersan.keyvalue.database.Table
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileInputStream
import java.nio.charset.Charset
import java.util.*

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

object BackupUtils{
    const val KEY_ROOT_VERSION = "version"
    const val KEY_ROOT_TABLE = "table"
    const val KEY_ROOT_KEY_VALUE = "key_value"

    private val CHARSET = Charsets.UTF_8

    const val EXT_PASSWORD      : Byte = 0x01
    const val EXT_PASSWORD_NO   : Byte = 0x00
    const val EXT_PASSWORD_YES  : Byte = 0x00

    private val FILE_HEADER = byteArrayOf(byte(0x06), byte(0x98), byte(0x45), byte(0xFF), byte(0xCC))
    private val FILE_FOOTER = byteArrayOf(byte(0xC9), byte(0x95), byte(0x27), byte(0x00), byte(0xAA))

    private fun byte(int:Int):Byte{
        return int.toByte()
    }

    fun toJson(tableList:List<Table>, keyValueList:List<KeyValue>, version:Int):String{
        val json = JSONObject()
        // 其他信息
        json.put(KEY_ROOT_VERSION, version)
        // 表信息
        val tableArrayJson = JSONArray()
        tableList.forEach { table ->
            tableArrayJson.put(table.toJsonObject())
        }
        json.put(KEY_ROOT_TABLE, tableArrayJson)
        // 键值对信息
        val keyValueArrayJson = JSONArray()
        keyValueList.forEach {  keyValue ->
            keyValueArrayJson.put(keyValue.toJsonObject())
        }
        json.put(KEY_ROOT_KEY_VALUE, keyValueArrayJson)
        // 返回JSON字符串
        return json.toString()
    }
    fun toObject(jsonStr:String, callback:(tableList:List<Table>, keyValueList:List<KeyValue>, version:Int)->Unit){
        val rootJson = JSONObject(jsonStr)
        // 版本
        var version = 0
        version = rootJson.optInt(KEY_ROOT_VERSION, version)
        // 表列表
        val tableList = ArrayList<Table>()
        val tableJsonArray = rootJson.optJSONArray(KEY_ROOT_TABLE)
        for (i in 0 until tableJsonArray.length()){
            val tableJson = tableJsonArray.getJSONObject(i)
            val table = Table()
            table.title = tableJson.optString(Table.KEY_TITLE)
            table.subtitle = tableJson.optString(Table.KEY_SUBTITLE)
            table.icon = tableJson.optInt(Table.KEY_ICON, 0)
            table.createTime = tableJson.optLong(Table.KEY_CREATE_TIME, 0)
            table.modifyTime = tableJson.optLong(Table.KEY_MODIFY_TIME, 0)
            table.extra = tableJson.optString(Table.KEY_EXTRA)
            tableList.add(table)
        }
        // 键值对列表
        val keyValueList = ArrayList<KeyValue>()
        val keyValueJsonArray = rootJson.optJSONArray(KEY_ROOT_KEY_VALUE)
        for (i in 0 until keyValueJsonArray.length()){
            val keyValueJson = keyValueJsonArray.getJSONObject(i)
            val keyValue = KeyValue()
            keyValue.title = keyValueJson.optString(KeyValue.KEY_TITLE)
            keyValue.value = keyValueJson.optString(KeyValue.KEY_VALUE)
            keyValue.table = keyValueJson.optInt(KeyValue.KEY_TABLE, 0)
            keyValue.icon = keyValueJson.optInt(KeyValue.KEY_ICON, 0)
            keyValue.createTime = keyValueJson.optLong(KeyValue.KEY_CREATE_TIME, 0)
            keyValue.modifyTime = keyValueJson.optLong(KeyValue.KEY_MODIFY_TIME, 0)
            keyValue.extra = keyValueJson.optString(KeyValue.KEY_EXTRA)
            keyValueList.add(keyValue)
        }
        // 回调
        callback.invoke(tableList, keyValueList, version)
    }

    fun writeIntoFile(file: File, content:String, vararg exts:Pair<Byte, ByteArray>){
        if(file.exists()){
            file.delete()
        }
        file.createNewFile()
        val outputSteam = file.outputStream()
        // 写 HEADER
        outputSteam.write(FILE_HEADER)
        // 写 EXT
        outputSteam.write(0) // TODO : 这里固定EXT为0
        // 写 CONTENT
        val contextBase64 = content.toByteArray(CHARSET)
        outputSteam.write(contextBase64.size.to4Byte())
        outputSteam.write(contextBase64) // TODO : 写入长度，这里还没实现
        // 写 FOOTER
        outputSteam.write(FILE_FOOTER)
        // 结束
        outputSteam.flush()
        outputSteam.close()
    }

    fun readFromFile(file:File, password:String):String{
        if (!file.exists() || file.isDirectory){
            return ""
        }
        var cache = ByteArray(5){0}
        val inSteam = file.inputStream()
        // 读取头部
        inSteam.read(cache)
        if (!cache.isSame(FILE_HEADER)){
            return ""
        }
        // 读取Ext
        cache = ByteArray(1){0}
        inSteam.read(cache)
        val extCount = cache[0].toUByte().toInt()
        for(i in 0 until extCount){
            cache = ByteArray(1){0}
            inSteam.read(cache)
            val extSize = cache[0].toUByte().toInt()
            for (j in 0 until extSize){
                cache = ByteArray(extSize){0}
                inSteam.read(cache)
            }
        }
        // 读取content
        cache = ByteArray(4){0}
        inSteam.read(cache)
        val size = cache.toInt()
        cache = ByteArray(size){0}
        inSteam.read(cache)
        // TODO : 校验Footer
        return String(cache, CHARSET)
    }

    fun getBackupFileList():ArrayList<File>{
        val result = ArrayList<File>()
        return result
    }
}
