package com.creepersan.keyvalue.ui

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.creepersan.keyvalue.R
import com.creepersan.keyvalue.base.BaseActivity
import com.creepersan.keyvalue.bean.setting.BaseSettingItem
import kotlinx.android.synthetic.main.activity_setting.*

class SettingView : BaseActivity() {

    override val layoutID: Int = R.layout.activity_setting

    private val adapter by lazy { SettingListAdapter() }
    private lateinit var settingItemList : ArrayList<BaseSettingItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSettingItem()
        initSettingView()
    }


    private fun initSettingItem(){

    }
    private fun initSettingView(){
        settingList.layoutManager = LinearLayoutManager(this)
        settingList.adapter = adapter
    }


    /**
     *  Inner class
     */
    private inner class SettingListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        }

        override fun getItemCount(): Int {

        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        }

    }
}