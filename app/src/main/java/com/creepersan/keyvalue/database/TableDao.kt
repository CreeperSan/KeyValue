package com.creepersan.keyvalue.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface TableDao {

    @Query("SELECT * FROM ${Table.TABLE_NAME}")
    fun getAllTable():List<Table>

    @Insert
    fun insertTable(vararg table:Table)

    @Delete
    fun deleteTable(table:Table)

}