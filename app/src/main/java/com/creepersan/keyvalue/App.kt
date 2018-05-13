package com.creepersan.keyvalue

import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import com.creepersan.keyvalue.database.DBKey
import com.creepersan.keyvalue.database.DatabaseManager
import java.lang.ref.WeakReference
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class App : Application() {

    companion object {
        private lateinit var mDatabaseManager: DatabaseManager
        private lateinit var mExecutor : ExecutorService


        /**
         *  获取一些单例数据
         */
        fun getDatabaseManger():DatabaseManager{
            return mDatabaseManager
        }
    }

    /**
     *  生命周期
     */
    override fun onCreate() {
        super.onCreate()
        initDatabase()
        initThreadExecute()
    }

    fun onDestroy(){
        mExecutor.shutdown()
    }


    /**
     *  初始化
     */
    private fun initDatabase(){
        mDatabaseManager = DatabaseManager()
        mDatabaseManager.init(DBKey.NAME_DATABASE, this)
    }
    private fun initThreadExecute(){
        mExecutor = Executors.newSingleThreadExecutor()
    }


    /**
     *  线程池相关操作
     */
    fun <T,A> submitTask(context:WeakReference<A>, action:()->T, resultAction:(context:A,result:T)->Unit){
//        mExecutor.execute {
            val result = action()
            context.get()?.apply {
                resultAction(this@apply,result)
            }
//        }
    }


}