package com.creepersan.keyvalue.util

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import com.creepersan.keyvalue.R

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


    private fun Context.getLayoutInflater():LayoutInflater{
        return LayoutInflater.from(this)
    }

}