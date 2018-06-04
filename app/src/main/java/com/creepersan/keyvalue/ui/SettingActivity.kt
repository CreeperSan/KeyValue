package com.creepersan.keyvalue.ui

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.creepersan.keyvalue.R
import com.creepersan.keyvalue.base.BaseActivity
import com.creepersan.keyvalue.bean.setting.BaseSettingItem
import com.creepersan.keyvalue.bean.setting.SettingGroupBean
import com.creepersan.keyvalue.bean.setting.SettingNormalBean
import com.creepersan.keyvalue.widget.setting.SettingGroupViewHolder
import com.creepersan.keyvalue.widget.setting.SettingNormalViewHolder
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : BaseActivity() {

    override val layoutID: Int = R.layout.activity_setting

    private val adapter by lazy { SettingListAdapter() }
    private val settingItemList by lazy { ArrayList<BaseSettingItem>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActionBar()
        initSettingItem()
        initSettingView()
    }


    private fun initActionBar(){
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    private fun initSettingItem(){
        // Backup Part
        settingItemList.add(SettingGroupBean(getString(R.string.settingGroupBackup)))
        settingItemList.add(SettingNormalBean(R.drawable.ic_import_black_24dp ,getString(R.string.settingItemImport),getString(R.string.settingDescriptionImport)))
        settingItemList.add(SettingNormalBean(R.drawable.ic_export_black_24dp ,getString(R.string.settingItemExport),getString(R.string.settingDescriptionExport)))
        // About Part
    }
    private fun initSettingView(){
        settingList.layoutManager = LinearLayoutManager(this)
        settingList.adapter = adapter
    }


    /* Menu */

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> { finish() }
        }
        return super.onOptionsItemSelected(item)
    }

    /* Inner class */
    private inner class SettingListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

        override fun getItemViewType(position: Int): Int {
            return settingItemList[position].type
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            when(viewType){
                BaseSettingItem.TYPE_GROUP  -> { return SettingGroupViewHolder(this@SettingActivity, parent) }
                BaseSettingItem.TYPE_NORMAl -> { return SettingNormalViewHolder(this@SettingActivity, parent) }
            }
            return SettingNormalViewHolder(this@SettingActivity, parent)
        }

        override fun getItemCount(): Int {
            return settingItemList.size
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            when(holder.itemViewType){
                BaseSettingItem.TYPE_GROUP  -> { initGroupViewHolder ( holder as SettingGroupViewHolder, settingItemList[position] as SettingGroupBean ) }
                BaseSettingItem.TYPE_NORMAl -> { initNormalViewHolder( holder as SettingNormalViewHolder, settingItemList[position] as SettingNormalBean ) }
            }
        }

        private fun initGroupViewHolder(holder: SettingGroupViewHolder, bean:SettingGroupBean){
            holder.setText(bean.title)
        }
        private fun initNormalViewHolder(holder: SettingNormalViewHolder, bean:SettingNormalBean){
            holder.iconView.setImageResource(bean.icon)
            holder.titleView.text = bean.title
            holder.descriptionView.text = bean.description
        }

    }
}