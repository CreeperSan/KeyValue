package com.creepersan.keyvalue.database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = Table.TABLE_NAME)
class Table {
    companion object{
        const val TABLE_NAME = "KeyValueTable"
        const val KEY_TITLE = "title"
        const val KEY_SUBTITLE = "subtitle"
        const val KEY_ICON = "icon"
        const val KEY_CREATE_TIME = "create_time"
        const val KEY_MODIFY_TIME = "modify_time"
        const val KEY_EXTRA = "extra"
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



}