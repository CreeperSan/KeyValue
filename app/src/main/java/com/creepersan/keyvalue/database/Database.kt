package com.creepersan.keyvalue.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.creepersan.keyvalue.database.Database.Companion.DATABASE_VERSION

@Database(entities = [KeyValue::class, Table::class], version = DATABASE_VERSION)
abstract class Database : RoomDatabase(){

    companion object{
        const val DATABASE_VERSION = 1

        const val NAME = "KeyValueNew.db"
    }

    abstract fun getKeyValueDao():KeyValueDao

    abstract fun getTableDao():TableDao

}