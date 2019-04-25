package com.creepersan.keyvalue.activity

import android.app.Activity
import android.app.AlertDialog
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
import com.creepersan.keyvalue.database.KeyValue
import kotlinx.android.synthetic.main.activity_add_key_value.*
import org.json.JSONObject
import java.lang.Exception
import java.util.ArrayList

class AddKeyValueActivity : BaseActivity() {

    companion object {
        const val KEY_INTENT_TABLE = "TableID"
        const val DEFAULT_INTENT_TABLE = -1
        const val KEY_INTENT_KEY_VALUE_ID = "KeyValueID"
        const val DEFAULT_INTENT_KEY_VALUE_ID = Int.MIN_VALUE

        private const val LIST_TYPE_UNDEFINE = -1
        private const val LIST_TYPE_KEY_VALUE = 0
        private const val LIST_TYPE_BUTTON = 1
        private const val LIST_TYPE_TITLE = 2
    }

    private val mAdapter by lazy { KeyValueAdapter()}
    private val mItemList = ArrayList<Bean>()
    private var mTable = 0
    private var mID = DEFAULT_INTENT_KEY_VALUE_ID // 用于标志是编辑还是添加
    private lateinit var mKeyValue : KeyValue

    override val layoutID: Int = R.layout.activity_add_key_value

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!initTableID()){
            toast(R.string.addKeyValueAddValueTitleToastMissingTableID)
            finish()
            return
        }
        initItemList()
        initToolbar()
        initRecyclerView()
    }

    private fun initTableID():Boolean{
        val table = intent.getIntExtra(KEY_INTENT_TABLE, DEFAULT_INTENT_TABLE)
        return if (table == DEFAULT_INTENT_TABLE){
            false
        }else{
            mTable = table
            true
        }
    }
    private fun initItemList(){
        // 添加标题
        mItemList.add(TitleBean())
        // 根据是编辑还是新建来生成列表
        mID = intent.getIntExtra(KEY_INTENT_KEY_VALUE_ID, DEFAULT_INTENT_KEY_VALUE_ID)
        if (isAdd()){
            mKeyValue = KeyValue()
            mItemList.add(KeyValueBean("", ""))
        }else{
            mKeyValue = getKeyValueDao().getKeyValue(mID)
            val valueStr = mKeyValue.value
            try {
                val json = JSONObject(valueStr)
                json.keys().forEach {  key ->
                    mItemList.add(KeyValueBean(key, json.optString(key, "[ Error ]")))
                }
            }catch (e:Exception){
                e.printStackTrace()
                toast("解析失败")
                finish()
            }
        }
        // 添加按钮
        mItemList.add(ButtonBean(R.string.addKeyValueAddItem.toString(this), View.OnClickListener {
            onAddKeyValueClick()
        }))
        mItemList.add(ButtonBean(R.string.addKeyValueSave.toString(this), View.OnClickListener {
            onSaveKeyValueClick()
        }))
        // 刷新界面
        mAdapter.notifyDataSetChanged()
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


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /* 属性判断 */
    private fun isEdit():Boolean{
        return mID != DEFAULT_INTENT_KEY_VALUE_ID
    }
    private fun isAdd():Boolean{
        return mID == DEFAULT_INTENT_KEY_VALUE_ID
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
        // 检查标题是否填写
        if (mKeyValue.title.trim() == ""){
            toast(R.string.addKeyValueAddValueTitleDialogHint)
            return
        }
        // 检查键是否重复
        val tmpKeySet = HashSet<String>()
        mItemList.forEach { bean ->
            if (bean is KeyValueBean){
                if (bean.key == "" || bean.value == ""){
                    toast("不能为空哦")
                    return
                }
                if (tmpKeySet.contains(bean.key)){
                    toast("键不能重复哦")
                    return
                }
                tmpKeySet.add(bean.key)
            }
        }
        // 生成数据库存储对象
        val json = JSONObject()
        mItemList.forEach { bean ->
            if (bean is KeyValueBean){
                json.put(bean.key, bean.value)
            }
        }
        // 保存到数据库
        val dao = getKeyValueDao()
        val currentTime = System.currentTimeMillis()
        if (isAdd()){
            mKeyValue.value = json.toString()
            mKeyValue.table = mTable
            mKeyValue.createTime = currentTime
            mKeyValue.modifyTime = currentTime
            mKeyValue.extra = ""
            dao.insertKeyValue(mKeyValue)
        }else{
            mKeyValue.value = json.toString()
            mKeyValue.modifyTime = currentTime
            dao.updateKeyValue(mKeyValue)
        }
        // 结束
        setResult(Activity.RESULT_OK)
        finish()
    }

    /* Exception */
    private class UnsupportedViewHolderException : Exception()

    /* KeyValueBean */
    private interface Bean
    private class TitleBean : Bean
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
                    .setPositiveButton(R.string.addKeyValueDialogPosButton.toString(context)) { _, _ ->
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
            return when(mItemList[position]){
                is TitleBean -> LIST_TYPE_TITLE
                is ButtonBean -> LIST_TYPE_BUTTON
                is KeyValueBean -> LIST_TYPE_KEY_VALUE
                else -> LIST_TYPE_UNDEFINE
            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, p1: Int): RecyclerView.ViewHolder {
            return when(p1){
                LIST_TYPE_TITLE -> {
                    TitleHolder(layoutInflater.inflate(R.layout.item_add_key_value_title, parent, false))
                }
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
                    holder.setNum(pos)
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
                is TitleBean -> {
                    val holder = tmpHolder as TitleHolder
                    holder.setContentText(mKeyValue.title)
                    holder.setOnClickListener(View.OnClickListener {
                        mEditTextDialog.setHint(R.string.addKeyValueAddValueTitleDialogHint.toString(this@AddKeyValueActivity))
                        mEditTextDialog.setText(mKeyValue.title)
                        mEditTextDialog.setOnConfirmListener {context ->
                            mKeyValue.title = context
                            holder.setContentText(mKeyValue.title)
                        }
                        mEditTextDialog.showDialog()
                    })
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
    private inner class TitleHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        val contentTextView = itemView.findViewById<TextView>(R.id.itemAddKeyValueItemTitle)

        fun setContentText(text:String){
            contentTextView.text = text
        }

        fun setOnClickListener(listener: View.OnClickListener){
            contentTextView.setOnClickListener(listener)
        }
    }
}