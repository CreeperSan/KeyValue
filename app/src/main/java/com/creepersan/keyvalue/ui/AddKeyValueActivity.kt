package com.creepersan.keyvalue.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.creepersan.keyvalue.R
import com.creepersan.keyvalue.base.BaseActivity
import com.creepersan.keyvalue.base.toString
import kotlinx.android.synthetic.main.activity_add_key_value.*
import org.w3c.dom.Text
import java.lang.Exception
import java.util.ArrayList

class AddKeyValueActivity : BaseActivity() {

    companion object {
        private const val LIST_TYPE_UNDEFINE = -1
        private const val LIST_TYPE_KEY_VALUE = 0
        private const val LIST_TYPE_BUTTON = 1
    }

    private val mAdapter by lazy { KeyValueAdapter()}
    private val mItemList = ArrayList<Bean>()

    override val layoutID: Int = R.layout.activity_add_key_value

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToolbar()
        initRecyclerView()
        initBeanData()
    }

    private fun initToolbar(){
        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
    }
    private fun initRecyclerView(){
        addKeyValueList.layoutManager = LinearLayoutManager(this)
        addKeyValueList.adapter = mAdapter
    }
    private fun initBeanData(){
        mItemList.add(KeyValueBean("", ""))
        mItemList.add(ButtonBean(R.string.addKeyValueAddItem.toString(this), View.OnClickListener {
            onAddKeyValueClick()
        }))
        mItemList.add(ButtonBean(R.string.addKeyValueSave.toString(this), View.OnClickListener {
            onSaveKeyValueClick()
        }))
        mAdapter.notifyDataSetChanged()
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /* 按键时间 */
    private fun onAddKeyValueClick(){
        for (i in 0 until mItemList.size){
            val index = mItemList.size - 1 - i
            val bean = mItemList[index]
            if (bean is KeyValueBean){
                mItemList.add(index+1, KeyValueBean("", "")) // 添加空键值对
                mAdapter.notifyItemInserted(index+1) // 通知刷新
                return
            }
        }
    }
    private fun onSaveKeyValueClick(){
        // 检查数据
        // 结束
        finish()
    }

    /* Exception */
    private class UnsupportedViewHolderException : Exception()

    /* KeyValueBean */
    private interface Bean
    private data class ButtonBean(var title:String, var onClickListener: View.OnClickListener) : Bean
    private data class KeyValueBean(var key: String, var value: String) : Bean


    /* 内部类 */
    private inner class KeyValueAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
        private val mEditTextDialog by lazy {
            val context = this@AddKeyValueActivity
            val editText = EditText(context)
            var onDialogConfirmListener : ((content:String) -> Unit)? = null
            editText.setHintTextColor(resources.getColor(R.color.gray, theme))
            editText.setTextColor(resources.getColor(R.color.black, theme))
            val dialog = AlertDialog.Builder(context)
                    .setView(editText)
                    .setPositiveButton(R.string.addKeyValueDialogPosButton.toString(context)) { dialog, which ->
                        onDialogConfirmListener?.invoke(editText.text.toString())
                    }
                    .setNegativeButton(R.string.addKeyValueDialogNegButton.toString(context), null)
                    .setOnDismissListener {
                        onDialogConfirmListener = null
                    }
                    .create()

            return@lazy object{
                fun setHint(hint:String){
                    editText.hint = hint
                }

                fun setText(text:String){
                    editText.setText(text)
                }

                fun setOnConfirmListener(listener:((content:String) -> Unit)?){
                    onDialogConfirmListener = listener
                }

                fun showDialog(){
                    dialog.show()
                }

                fun hideDialog(){
                    dialog.dismiss()
                }
            }
        }

        override fun getItemViewType(position: Int): Int {
            val item = mItemList[position]
            return when(item){
                is ButtonBean -> LIST_TYPE_BUTTON
                is KeyValueBean -> LIST_TYPE_KEY_VALUE
                else -> LIST_TYPE_UNDEFINE
            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, p1: Int): RecyclerView.ViewHolder {
            return when(p1){
                LIST_TYPE_KEY_VALUE -> {
                    KeyValueHolder(layoutInflater.inflate(R.layout.item_add_key_value_item, parent, false))
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

        override fun onBindViewHolder(tmpHolder: RecyclerView.ViewHolder, pos: Int) {
            val context = this@AddKeyValueActivity
            when(val bean = mItemList[pos]){
                is KeyValueBean -> {
                    val holder = tmpHolder as KeyValueHolder
                    holder.showContent(bean.key, bean.value)
                    holder.setNum(pos+1)
                    holder.setKeyClickListener(View.OnClickListener {
                        mEditTextDialog.setHint(R.string.addKeyValueAddKeyHint.toString(context))
                        mEditTextDialog.setText(bean.key)
                        mEditTextDialog.setOnConfirmListener { content ->
                            bean.key = content
                            mAdapter.notifyItemChanged(pos)
                        }
                        mEditTextDialog.showDialog()
                    })
                    holder.setValueClickListener(View.OnClickListener {
                        mEditTextDialog.setHint(R.string.addKeyValueAddValueHint.toString(context))
                        mEditTextDialog.setText(bean.value)
                        mEditTextDialog.setOnConfirmListener { content ->
                            bean.value = content
                            mAdapter.notifyItemChanged(pos)
                        }
                        mEditTextDialog.showDialog()
                    })
                }
                is ButtonBean -> {
                    val holder = tmpHolder as ButtonViewHolder
                    holder.showButton(bean.title, bean.onClickListener)
                }
            }
        }

    }
    private inner class ButtonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val button = itemView as Button

        fun showButton(title:String, onClickListener:View.OnClickListener){
            button.text = title
            button.setOnClickListener(onClickListener)
        }
    }
    private inner class KeyValueHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val numTextView = itemView.findViewById<TextView>(R.id.itemAddKeyValueItemNum)
        private val keyTextView = itemView.findViewById<TextView>(R.id.itemAddKeyValueItemKey)
        private val valueTextView = itemView.findViewById<TextView>(R.id.itemAddKeyValueItemValue)

        fun showContent(key:String, value:String){
            if (key.isEmpty()){
                keyTextView.setTextColor(resources.getColor(R.color.gray, theme))
                keyTextView.setText(R.string.addKeyValueAddKeyHint)
            }else{
                keyTextView.setTextColor(resources.getColor(R.color.black, theme))
                keyTextView.text = key
            }
            if (value.isEmpty()){
                valueTextView.setTextColor(resources.getColor(R.color.gray, theme))
                valueTextView.setText(R.string.addKeyValueAddValueHint)
            }else{
                valueTextView.setTextColor(resources.getColor(R.color.black, theme))
                valueTextView.text = value
            }
        }

        fun setNum(num:Int){
            numTextView.text = num.toString()
        }

        fun getKey():String = keyTextView.text.toString().trim()
        fun getValue():String = valueTextView.text.toString().trim()

        fun setKeyClickListener(listener:View.OnClickListener){
            keyTextView.setOnClickListener(listener)
        }

        fun setValueClickListener(listener: View.OnClickListener){
            valueTextView.setOnClickListener(listener)
        }

    }
}