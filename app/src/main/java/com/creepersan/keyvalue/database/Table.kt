package com.creepersan.keyvalue.database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.alibaba.fastjson.JSONObject


@Entity(tableName = Table.TABLE_NAME)
class Table {
    companion object{
        const val TABLE_NAME = "KeyValueTable"
        const val KEY_ID = "id"
        const val KEY_TITLE = "title"
        const val KEY_SUBTITLE = "subtitle"
        const val KEY_ICON = "icon"
        const val KEY_CREATE_TIME = "create_time"
        const val KEY_MODIFY_TIME = "modify_time"
        const val KEY_EXTRA = "extra"

        private fun JSONObject.optString(key:String):String{
            return this.getString(key) ?: ""
        }

        fun fromJsonObject(json:JSONObject):Table{
            val table = Table()
            table.title = json.optString(KEY_TITLE)
            table.subtitle = json.optString(KEY_SUBTITLE)
            table.icon = json.getIntValue(KEY_ICON)
            table.createTime = json.getLongValue(KEY_CREATE_TIME)
            table.modifyTime = json.getLongValue(KEY_MODIFY_TIME)
            table.extra = json.optString(KEY_EXTRA)
            table.id = json.getIntValue(KEY_ID)
            return table
        }
    }

    @PrimaryKey(autoGenerate = true)
    var id = 0

    @ColumnInfo(name = KEY_TITLE)
    var title = ""

    @ColumnInfo(name = KEY_SUBTITLE)
    var subtitle = ""

    @ColumnInfo(name = KEY_ICON)
    var icon = 0

    @ColumnInfo(name = KEY_CREATE_TIME)
    var createTime = 0L

    @ColumnInfo(name = KEY_MODIFY_TIME)
    var modifyTime = 0L

    @ColumnInfo(name = KEY_EXTRA)
    var extra = ""

    fun toJsonObject(): JSONObject {
        val json = JSONObject()
        json.put(KEY_TITLE, title)
        json.put(KEY_SUBTITLE, subtitle)
        json.put(KEY_ICON, icon)
        json.put(KEY_CREATE_TIME, createTime)
        json.put(KEY_MODIFY_TIME, modifyTime)
        json.put(KEY_EXTRA, extra)
        json.put(KEY_ID, id)
        return json
    }

}