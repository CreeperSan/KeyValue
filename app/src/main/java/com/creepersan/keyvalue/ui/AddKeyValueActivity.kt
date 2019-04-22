package com.creepersan.keyvalue.ui

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.creepersan.keyvalue.R
import com.creepersan.keyvalue.base.BaseActivity
import com.creepersan.keyvalue.base.toString
import java.lang.Exception

class AddKeyValueActivity : BaseActivity() {

    companion object {
        private const val LIST_TYPE_UNDEFINE = -1
        private const val LIST_TYPE_ITEM = 0
        private const val LIST_TYPE_BUTTON = 1
    }

    private val mItemList = ArrayList<Bean>()

    override val layoutID: Int = R.layout.activity_add_key_value

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView(){

    }

    /* Exception */
    private class UnsupportedViewHolderException : Exception()

    /* ItemBean */
    private interface Bean
    private data class ButtonBean(var title:String, var onClickListener: View.OnClickListener) : Bean
    private data class ItemBean(var key: String, var value: String) : Bean


    /* 内部类 */
    private inner class KeyValueAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

        override fun getItemViewType(position: Int): Int {
            val item = mItemList[position]
            return when(item){
                is ButtonBean -> LIST_TYPE_BUTTON
                is ItemBean -> LIST_TYPE_ITEM
                else -> LIST_TYPE_UNDEFINE
            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, p1: Int): RecyclerView.ViewHolder {
            return when(p1){
                LIST_TYPE_ITEM -> {
                    ItemViewHolder(layoutInflater.inflate(R.layout.item_add_key_value_item, parent, false))
                }
                LIST_TYPE_BUTTON -> {
                    ButtonViewHolder(layoutInflater.inflate(R.layout.item_add_key_value_button, parent, false))
                }
                else -> {
                    throw UnsupportedViewHolderException()
                }
            }
        }

        override fun getItemCount(): Int {
            return mItemList.size
        }

        override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {

        }

    }
    private inner class ButtonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val button = itemView as Button

        fun showButton(title:String, onClickListener:View.OnClickListener){
            button.text = title
            button.setOnClickListener(onClickListener)
        }
    }
    private inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val keyEditText = itemView.findViewById<EditText>(R.id.itemAddKeyValueItemKey)
        private val valueEditText = itemView.findViewById<EditText>(R.id.itemAddKeyValueItemValue)

        fun showContent(key:String, value:String){
            keyEditText.hint = R.string.addKeyValueKey.toString(this@AddKeyValueActivity)
            valueEditText.hint = R.string.addKeyValueKey.toString(this@AddKeyValueActivity)
        }

        fun getKey():String = keyEditText.text.toString().trim()
        fun getValue():String = valueEditText.text.toString().trim()
    }
}