package com.creepersan.keyvalue.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [KeyValue::class, Table::class], version = 1)
abstract class Database : RoomDatabase(){
    companion object{
        const val NAME = "KeyValueNew.db"
    }

    abstract fun getKeyValueDao():KeyValueDao

    abstract fun getTableDao():TableDao

}