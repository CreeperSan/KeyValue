package com.creepersan.keyvalue.ui

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.creepersan.keyvalue.R
import com.creepersan.keyvalue.base.BaseActivity
import com.creepersan.keyvalue.base.BaseViewHolder
import com.creepersan.keyvalue.database.KeyValuePair
import com.creepersan.keyvalue.util.IconUtil
import com.creepersan.keyvalue.database.Table
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.collections.ArrayList

class MainActivity : BaseActivity() {

    private companion object {
        const val REQUEST_CODE_TABLE_EDIT = 1
        const val REQUEST_CODE_VALUE_EDIT = 2
    }

    override val layoutID: Int = R.layout.activity_main

    private var mTableList = ArrayList<Table>()
    private var mValueList = ArrayList<KeyValuePair>()
    private val mTableAdapter by lazy { TableAdapter() }
    private val mValueAdapter by lazy { KeyValueAdapter() }
    private var mTableID = KeyValueAddActivity.VAL_DEFAULT_INTENT_TABLE_ID

    /**
     *  生命周期回调
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initTableList()
        initTableData()
        initValueList()
        initFloatButton()
//        toActivity(RandomGenerateActivity::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        app.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            REQUEST_CODE_TABLE_EDIT -> {
                if (resultCode == Activity.RESULT_OK){
                    initTableData()
                }
            }
            REQUEST_CODE_VALUE_EDIT -> {
                if (resultCode == Activity.RESULT_OK){
                    initValueData()
                }
            }
        }
    }


    /* Menu */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.menuMainSetting -> { toActivity(SettingActivity::class.java) }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     *  Init
     */

    private fun initTableList(){
        mainTableList.layoutManager = LinearLayoutManager(this)
        mainTableList.adapter = mTableAdapter
    }
    private fun initTableData(){
        mTableList.clear()
        submitTask(this,{
            return@submitTask getDatabaseManager().getAllTableList()
        },{ activity, result ->
            activity.mTableList = result
            activity.mTableAdapter.notifyDataSetChanged()
        })
    }
    private fun initValueList(){
        mainContentList.layoutManager = LinearLayoutManager(this)
        mainContentList.adapter = mValueAdapter
    }
    private fun initValueData(){
        mValueList.clear()
        submitTask(this, {
            return@submitTask getDatabaseManager().getAllKeyValueListInTable(mTableID)
        },{ activity, result ->
            activity.mValueList = result
            activity.mValueAdapter.notifyDataSetChanged()
        })
    }
    private fun initFloatButton(){
        mainAddFab.setOnClickListener{
            startActivityForResult(Intent(this@MainActivity, KeyValueAddActivity::class.java).apply {
                putExtra(KeyValueAddActivity.KEY_INTENT_TABLE_ID, mTableID)
            }, REQUEST_CODE_VALUE_EDIT)
        }
        mainAddFab.setOnLongClickListener {
            startActivity(Intent(this@MainActivity, AddKeyValueActivity::class.java))
            return@setOnLongClickListener true
        }
    }

    /**
     *  UI Operation
     */
    private fun showAsHint(){
        mainContentEmptyLayout.setVisible()
        mainContentList.setGone()
    }
    private fun showAsData(){
        mainContentEmptyLayout.setGone()
        mainContentList.setVisible()
    }

    /**
     *  Inner Class
     */
    private inner class TableAdapter : RecyclerView.Adapter<TableViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TableViewHolder = TableViewHolder(parent)
        override fun getItemCount(): Int = mTableList.size +1
        override fun onBindViewHolder(holder: TableViewHolder, position: Int) {
            if (position >= mTableList.size){   // 如果是添加
                holder.setIcon(IconUtil.getReserveIcon(IconUtil.ICON_RESERVE_ADD))
                holder.setContent(getString(R.string.mainTableAdd), getString(R.string.mainTableAddHint))
                holder.itemView.setOnClickListener {
                    startActivityForResult(Intent(this@MainActivity, TableAddActivity::class.java), REQUEST_CODE_TABLE_EDIT)
                }
                holder.itemView.setOnLongClickListener { false }
            }else{  // 如果是列表
                val tableItem = mTableList[position]
                holder.setIcon(IconUtil.getIcon(tableItem.icon))
                holder.setContent(tableItem.name, tableItem.description)
                holder.itemView.setOnClickListener {
                    mTableID = tableItem.id
                    mainAddFab.visibility = View.VISIBLE
                    title = tableItem.name
                    initValueData()
                    showAsData()
                    mainDrawerLayout.closeDrawers()
                }
                holder.itemView.setOnLongClickListener {
                    showAlert(message = getString(R.string.mainDeleteKeyValueHint),
                            posButton = DialogActionButton(getString(R.string.confirm),{
                                getDatabaseManager().deleteTable(tableItem.id)
                                toast(R.string.operationSuccess)
                                initTableData()
                                if (mTableID == tableItem.id){
                                    showAsHint()
                                    mTableID = KeyValueAddActivity.VAL_DEFAULT_INTENT_TABLE_ID
                                    mainAddFab.visibility = View.GONE
                                    title = getString(R.string.app_name)
                                }
                            }),
                            negButton = DialogActionButton(getString(R.string.cancel),{}))
                    true
                }
            }
        }
    }
    private inner class TableViewHolder(parent:ViewGroup) : BaseViewHolder(this,R.layout.item_table,parent){
        val icon : ImageView = itemView.findViewById(R.id.itemTableIcon)
        val title : TextView = itemView.findViewById(R.id.itemTableTitle)
        val extra : TextView = itemView.findViewById(R.id.itemTableExtra)

        fun setIcon(@DrawableRes resID:Int){
            icon.setImageResource(resID)
        }

        fun setContent(titleStr:String, contentStr:String?){
            title.setText(titleStr)
            if (contentStr != null && contentStr.length>0){
                extra.setVisible()
                extra.setText(contentStr)
            }else{
                extra.setGone()
            }
        }
    }
    private inner class KeyValueAdapter : RecyclerView.Adapter<KeyValueHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeyValueHolder = KeyValueHolder(parent)

        override fun getItemCount(): Int = mValueList.size

        override fun onBindViewHolder(holder: KeyValueHolder, position: Int) {
            val keyValueItem = mValueList[position]
            holder.setIcon(IconUtil.getIcon(keyValueItem.icon))
            holder.setTitle(keyValueItem.name)
            holder.itemView.setOnClickListener {
                AlertDialog.Builder(this@MainActivity)
                        .setMessage(keyValueItem.value)
                        .show()
            }
            holder.itemView.setOnLongClickListener {
                showAlert(message = getString(R.string.mainDeleteKeyValueHint),
                        posButton = DialogActionButton(getString(R.string.confirm), {
                            getDatabaseManager().deleteKeyValue(keyValueItem.id)
                            toast(R.string.operationSuccess)
                            initValueData()
                        }),
                        negButton = DialogActionButton(getString(R.string.cancel),{}))
                true
            }
        }

    }
    private inner class KeyValueHolder(parent:ViewGroup) : BaseViewHolder(this,R.layout.item_key_value_normal,parent){
        val icon : ImageView = itemView.findViewById(R.id.itemKeyValueNormalIcon)
        val key : TextView = itemView.findViewById(R.id.itemKeyValueNormalTitle)

        fun setIcon(@DrawableRes resID:Int){
            icon.setImageResource(resID)
        }

        fun setTitle(titleStr: String){
            key.text = titleStr
        }
    }

}
