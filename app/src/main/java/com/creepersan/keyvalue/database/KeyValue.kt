package com.creepersan.keyvalue.database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.alibaba.fastjson.JSONObject

@Entity(tableName = KeyValue.TABLE_NAME)
class KeyValue {
    companion object{
        const val TABLE_NAME = "KeyValueItem"
        const val KEY_ID = "id"
        const val KEY_TITLE = "title"
        const val KEY_VALUE = "value"
        const val KEY_ICON = "icon"
        const val KEY_TABLE = "table_id"
        const val KEY_CREATE_TIME = "create_time"
        const val KEY_MODIFY_TIME = "modify_time"
        const val KEY_EXTRA = "extra"

        private fun JSONObject.optString(key:String):String{
            return this.getString(key) ?: ""
        }

        fun fromJsonObject(jsonObject: JSONObject):KeyValue{
            val keyValue = KeyValue()
            keyValue.title = jsonObject.optString(KEY_TITLE)
            keyValue.value = jsonObject.optString(KEY_VALUE)
            keyValue.table = jsonObject.getIntValue(KEY_TABLE)
            keyValue.icon = jsonObject.getIntValue(KEY_ICON)
            keyValue.createTime = jsonObject.getLongValue(KEY_CREATE_TIME)
            keyValue.modifyTime = jsonObject.getLongValue(KEY_MODIFY_TIME)
            keyValue.extra = jsonObject.optString(KEY_EXTRA)
            keyValue.id = jsonObject.getIntValue(KEY_ID)
            return keyValue
        }
    }

    @PrimaryKey(autoGenerate = true)
    var id = 0

    @ColumnInfo(name = KEY_TITLE)
    var title = ""

    @ColumnInfo(name = KEY_VALUE)
    var value = ""

    @ColumnInfo(name = KEY_TABLE)
    var table = 0

    @ColumnInfo(name = KEY_ICON)
    var icon = 0

    @ColumnInfo(name = KEY_CREATE_TIME)
    var createTime = 0L

    @ColumnInfo(name = KEY_MODIFY_TIME)
    var modifyTime = 0L

    @ColumnInfo(name = KEY_EXTRA)
    var extra = ""

    fun toJsonObject():JSONObject{
        val json = JSONObject()
        json.put(KEY_TITLE, title)
        json.put(KEY_VALUE, value)
        json.put(KEY_TABLE, table)
        json.put(KEY_ICON, icon)
        json.put(KEY_CREATE_TIME, createTime)
        json.put(KEY_MODIFY_TIME, modifyTime)
        json.put(KEY_EXTRA, extra)
        json.put(KEY_ID, id)
        return json
    }


}