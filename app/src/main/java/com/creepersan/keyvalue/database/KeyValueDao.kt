package com.creepersan.keyvalue.database

import android.arch.persistence.room.*

@Dao
interface KeyValueDao {

    @Query("SELECT * FROM ${KeyValue.TABLE_NAME}")
    fun getAllKeyValue():List<KeyValue>

    @Query("SELECT * FROM ${KeyValue.TABLE_NAME} WHERE ${KeyValue.KEY_TABLE}=:tableID")
    fun getAllKeyValue(tableID:Int):List<KeyValue>

    @Query("SELECT * FROM ${KeyValue.TABLE_NAME} WHERE ${KeyValue.KEY_ID}=:keyValueID")
    fun getKeyValue(keyValueID:Int):KeyValue

    @Update
    fun updateKeyValue(vararg keyValue:KeyValue)

    @Insert
    fun insertKeyValue(vararg keyValue:KeyValue)

    @Delete
    fun deleteKeyValue(keyValue:KeyValue)

}