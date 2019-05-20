package com.creepersan.keyvalue.util

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import com.creepersan.keyvalue.R
import java.util.ArrayList

object DialogBuilder {

    fun createEditTextDialog(context: Context, title:String="", value:String="", hint:String="", posStr:String="", posAction:((value:String)->Unit)?=null, negStr:String="", negAction:(()->Unit)?=null): EditTextDialogController {
        val builder = AlertDialog.Builder(context)

        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_builder_edit_text, null)
        val editText = dialogView.findViewById<EditText>(R.id.dialogBuilderEditTextEditText)

        builder.setView(dialogView)

        if (title.isNotEmpty()){
            builder.setTitle(title)
        }
        if (posStr.isNotEmpty()){
            builder.setPositiveButton(posStr){ _, _ ->
                posAction?.invoke(editText.text.toString())
            }
        }
        if (negStr.isNotEmpty()){
            builder.setPositiveButton(negStr){ _, _ ->
                negAction?.invoke()
            }
        }

        val dialog = builder.create()

        return object : EditTextDialogController(dialog){
            override fun cancel() = dialog.cancel()
            override fun isShowing(): Boolean = dialog.isShowing
            override fun show() = dialog.show()
            override fun hide() = dialog.hide()
            override fun getValue(): String = editText.text.toString()
            override fun setHint(hint: String) {
                editText.hint = hint
            }
            override fun setValue(value: String) {
                editText.setText(value)
            }
        }
    }

    fun createLoadingDialog(context:Context, title:String="", hintStr:String="", cancelable:Boolean=false):LoadingDialogController{
        val builder = AlertDialog.Builder(context)
        val rootView = context.getLayoutInflater().inflate(R.layout.dialog_builder_loading, null)
        val progressBar = rootView.findViewById<ProgressBar>(R.id.dialogBuilderLoadingProgressBar)
        val textView = rootView.findViewById<TextView>(R.id.dialogBuilderLoadingHintText)
        if (title.isNotEmpty()){
            builder.setTitle(title)
        }
        builder.setView(rootView)
        builder.setCancelable(cancelable)
        val dialog = builder.create()
        return object : LoadingDialogController(dialog){
            override fun setHint(value: String) {
                textView.text = value
            }
        }
    }

    fun createMultiStringListDialog(context: Context, title: String="", dataList:ArrayList<String>, selectCallback:((index:Int, value:String)->Unit)?=null): MultiStringSelectController {
        val builde = AlertDialog.Builder(context)
        if (title.isNotEmpty()){
            builde.setTitle(title)
        }

        val view = context.getLayoutInflater().inflate(R.layout.dialog_builder_multi_string_select, null)
        val recyclerView = view.findViewById<RecyclerView>(R.id.dialogBuilderMultiStringList)

        builde.setView(view)

        val dialog = builde.create()



        class TextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            private val textView = itemView as TextView

            fun setText(title:String){
                textView.text = title
            }

            fun setOnClicklistner(listener:View.OnClickListener){
                textView.setOnClickListener(listener)
            }

        }
        class TextViewAdapter : RecyclerView.Adapter<TextViewHolder>(){
            override fun onCreateViewHolder(p0: ViewGroup, p1: Int): TextViewHolder {
                return TextViewHolder(context.getLayoutInflater().inflate(R.layout.item_dialog_builder_multi_string_select, p0, false))
            }

            override fun getItemCount(): Int {
                return dataList.size
            }

            override fun onBindViewHolder(holder: TextViewHolder, pos: Int) {
                val item = dataList[pos]
                holder.setText(item)
                holder.setOnClicklistner(View.OnClickListener {
                    selectCallback?.invoke(pos, item)
                    dialog.dismiss()
                })
            }

        }


        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = TextViewAdapter()


        return object : MultiStringSelectController(dialog){
            override fun refreshList() {
                recyclerView.adapter?.notifyDataSetChanged()
            }
        }
    }



    abstract class BaseDialogController(private val dialog:Dialog){
        open fun cancel(){
            dialog.cancel()
        }
        open fun isShowing():Boolean{
            return dialog.isShowing
        }
        open fun show(){
            return dialog.show()
        }
        open fun hide(){
            return dialog.hide()
        }
    }
    abstract class EditTextDialogController(dialog: Dialog):BaseDialogController(dialog){
         abstract fun setHint(hint:String)
         abstract fun setValue(value:String)
         abstract fun getValue():String
    }
    abstract class LoadingDialogController(dialog: Dialog):BaseDialogController(dialog){
        abstract fun setHint(value:String)
    }
    abstract class MultiStringSelectController(dialog: Dialog):BaseDialogController(dialog){
        abstract fun refreshList();
    }


    private fun Context.getLayoutInflater():LayoutInflater{
        return LayoutInflater.from(this)
    }

}