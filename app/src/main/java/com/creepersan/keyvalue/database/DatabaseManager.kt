package com.creepersan.keyvalue.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class DatabaseManager() {

    lateinit var database : SQLiteDatabase

    fun init(dbName:String, context:Context){
        // 打开数据库
        database = context.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null)
        // 初始化数据库表
        database.execSQL("CREATE TABLE IF NOT EXISTS ${DBKey.TABLE_TABLE}(" +
                "${DBKey.KEY_TABLE_ID} INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "${DBKey.KEY_TABLE_NAME} TEXT NOT NULL," +
                "${DBKey.KEY_TABLE_TYPE} INTEGER NOT NULL," +
                "${DBKey.KEY_TABLE_ICON} INTEGER NOT NULL," +
                "${DBKey.KEY_TABLE_CREATE_TIME} INTEGER NOT NULL," +
                "${DBKey.KEY_TABLE_DESCRIPTION} TEXT)")
        database.execSQL("CREATE TABLE IF NOT EXISTS ${DBKey.TABLE_KEY_VALUE_PAIR}(" +
                "${DBKey.KEY_KEY_VALUE_PAIR_ID} INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "${DBKey.KEY_KEY_VALUE_PAIR_NAME} TEXT NOT NULL," +
                "${DBKey.KEY_KEY_VALUE_PAIR_TYPE} INTEGER NOT NULL," +
                "${DBKey.KEY_KEY_VALUE_PAIR_ICON} INTEGER NOT NULL," +
                "${DBKey.KEY_KEY_VALUE_PAIR_VALUE} TEXT NOT NULL," +
                "${DBKey.KEY_KEY_VALUE_PAIR_TABLE} INTEGER NOT NULL," +
                "${DBKey.KEY_KEY_VALUE_PAIR_CREATE_TIME} INTEGER NOT NULL," +
                "${DBKey.KEY_KEY_VALUE_PAIR_DESCRIPTION} TEXT)")
    }

    /**
     *  表的操作
     */

    fun getAllTableList():ArrayList<Table>{
        val resultList = ArrayList<Table>()
        fastQuery(DBKey.TABLE_TABLE).apply {
            while (moveToNext()){
                resultList.add(getTable())
            }
            close()
        }
        return resultList
    }
    fun getAllKeyValueList():ArrayList<KeyValyePair>{
        val resultList = ArrayList<KeyValyePair>()
        fastQuery(DBKey.TABLE_KEY_VALUE_PAIR).apply {
            while(moveToNext()){
                resultList.add(getKeyValue())
            }
            close()
        }
        return resultList
    }
    fun getAllKeyValueListInTable(tableID:Int):ArrayList<KeyValyePair>{
        val resultList = ArrayList<KeyValyePair>()
        database.query(DBKey.TABLE_KEY_VALUE_PAIR,null,"${DBKey.KEY_KEY_VALUE_PAIR_TABLE} = ?", arrayOf(tableID.toString()),null,null,null).apply {
            while(moveToNext()){
                resultList.add(getKeyValue())
            }
            close()
        }
        return resultList
    }

    fun insertTable(name:String, type:Int, icon:Int, description:String){
        database.insert(DBKey.TABLE_TABLE,null,ContentValues().apply {
            put(DBKey.KEY_TABLE_NAME, name)
            put(DBKey.KEY_TABLE_TYPE, type)
            put(DBKey.KEY_TABLE_ICON, icon)
            put(DBKey.KEY_TABLE_CREATE_TIME, System.currentTimeMillis())
            put(DBKey.KEY_TABLE_DESCRIPTION, description)
        })
    }

    fun insertKeyValue(name:String, type:Int, icon:Int, value:String, table:Int, description:String){
        database.insert(DBKey.TABLE_KEY_VALUE_PAIR, null, ContentValues().apply {
            put(DBKey.KEY_KEY_VALUE_PAIR_NAME, name)
            put(DBKey.KEY_KEY_VALUE_PAIR_TYPE, type)
            put(DBKey.KEY_KEY_VALUE_PAIR_ICON, icon)
            put(DBKey.KEY_KEY_VALUE_PAIR_VALUE, value)
            put(DBKey.KEY_KEY_VALUE_PAIR_TABLE, table)
            put(DBKey.KEY_KEY_VALUE_PAIR_DESCRIPTION, description)
            put(DBKey.KEY_KEY_VALUE_PAIR_CREATE_TIME, System.currentTimeMillis())
        })
    }

    fun deleteKeyValue(id:Int){
        database.delete(DBKey.TABLE_KEY_VALUE_PAIR,"${DBKey.KEY_KEY_VALUE_PAIR_ID} = ?", arrayOf(id.toString()))
    }

    fun deleteTable(id:Int){
        database.delete(DBKey.TABLE_KEY_VALUE_PAIR, "${DBKey.KEY_KEY_VALUE_PAIR_TABLE} = ?", arrayOf(id.toString()))
        database.delete(DBKey.TABLE_TABLE, "${DBKey.KEY_TABLE_ID} = ?", arrayOf(id.toString()))
    }


    /**
     *  快速操作
     */
    private fun fastQuery(table:String): Cursor {
        return database.query(table,null,"${DBKey.KEY_TABLE_ID} > ?", arrayOf("0"),null,null,null)
    }

    /**
     *  拓展方法
     */
    private fun Cursor.getTable():Table{
        return Table(
                getInt(0),
                getString(1),
                getInt(2),
                getInt(3),
                getLong(4),
                getString(5)
        )
    }
    private fun Cursor.getKeyValue():KeyValyePair{
        return KeyValyePair(
                getInt(0),
                getString(1),
                getInt(2),
                getInt(3),
                getString(4),
                getInt(5),
                getLong(6),
                getString(7)
        )
    }


}