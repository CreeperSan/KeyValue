package com.creepersan.keyvalue.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface KeyValueDao {

    @Query("SELECT * FROM ${KeyValue.TABLE_NAME}")
    fun getAllKeyValue():List<KeyValue>

    @Insert
    fun insertKeyValue(vararg keyValue:KeyValue)

    @Delete
    fun deleteKeyValue(keyValue:KeyValue)

}