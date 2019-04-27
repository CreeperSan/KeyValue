package com.creepersan.keyvalue.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import org.json.JSONArray
import org.json.JSONObject

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

    fun getAllTableList():ArrayList<TableOld>{
        val resultList = ArrayList<TableOld>()
        fastQuery(DBKey.TABLE_TABLE).apply {
            while (moveToNext()){
                resultList.add(getTable())
            }
            close()
        }
        return resultList
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
    private fun Cursor.getTable():TableOld{
        return TableOld(
                getInt(0),
                getString(1),
                getInt(2),
                getInt(3),
                getLong(4),
                getString(5)
        )
    }

    private fun KeyValuePairOld.toJson():JSONObject{
        val jsonObject = JSONObject()
        jsonObject.put(DBKey.KEY_KEY_VALUE_PAIR_ID, this.id)
        jsonObject.put(DBKey.KEY_KEY_VALUE_PAIR_NAME, this.name)
        jsonObject.put(DBKey.KEY_KEY_VALUE_PAIR_TYPE, this.type)
        jsonObject.put(DBKey.KEY_KEY_VALUE_PAIR_ICON, this.icon)
        jsonObject.put(DBKey.KEY_KEY_VALUE_PAIR_VALUE, this.value)
        jsonObject.put(DBKey.KEY_KEY_VALUE_PAIR_TABLE, this.table)
        jsonObject.put(DBKey.KEY_KEY_VALUE_PAIR_DESCRIPTION, this.description)
        jsonObject.put(DBKey.KEY_KEY_VALUE_PAIR_CREATE_TIME, this.createTime)
        return jsonObject
    }


}