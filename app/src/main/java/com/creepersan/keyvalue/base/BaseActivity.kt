package com.creepersan.keyvalue.base

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.creepersan.keyvalue.App
import com.creepersan.keyvalue.database.DatabaseManager
import java.lang.ref.WeakReference

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {
    companion object {
        private val EMPTY_LAYOUT = 0
    }

    @LayoutRes
    open val layoutID : Int = EMPTY_LAYOUT

    protected val app by lazy { application as App }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLayout()
    }

    private fun initLayout(){
        if (layoutID != EMPTY_LAYOUT){
            setContentView(layoutID)
        }
    }

    /**
     *  提供的一些便捷方法
     */
    fun <T> toActivity(clazz:Class<T>, isFinish:Boolean = false){
        startActivity(Intent(this, clazz))
        if (isFinish){
            finish()
        }
    }

    fun log(content:String){
        Log.i(javaClass.simpleName, content)
    }

    fun toast(content:String, length:Int = Toast.LENGTH_SHORT){
       Toast.makeText(this,content,length).show()
    }

    fun toast(@StringRes content:Int, length: Int = Toast.LENGTH_SHORT){
        toast(getString(content,length))
    }

    fun <T,A> submitTask(context:A, action:()->T, resultAction:(context:A,result:T)->Unit){
        app.submitTask(WeakReference(context),action,resultAction)
    }

    fun getDatabaseManager():DatabaseManager{
        return App.getDatabaseManger()
    }

    fun showAlert(title:String? = null, message:String?=null,posButton:DialogActionButton?=null, negButton:DialogActionButton?=null){
        AlertDialog.Builder(this)
                .actionIf(title!=null, { self -> self.setTitle(title) })
                .actionIf(message!=null, { self -> self.setMessage(message) })
                .actionIf(posButton!=null, { self ->
                    self.setPositiveButton(posButton!!.title,{ _,_ -> posButton.action() })
                })
                .actionIf(negButton!=null, { self ->
                    self.setNegativeButton(negButton!!.title,{ _,_ -> negButton.action() })
                })
                .show()
    }

    class DialogActionButton(var title: String,var action:() -> Unit)

    /**
     *  拓展方法
     */
    fun View.setVisible(){
        visibility = View.VISIBLE
    }
    fun View.setInvisible(){
        visibility = View.INVISIBLE
    }
    fun View.setGone(){
        visibility = View.GONE
    }

    fun <T> T.actionIf(flag:Boolean, action: (self:T) -> Unit):T{
        if (flag) action(this)
        return this
    }
}