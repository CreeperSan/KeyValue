package com.creepersan.keyvalue.widget.setting

import android.content.Context
import android.view.ViewGroup
import android.widget.TextView
import com.creepersan.keyvalue.R
import com.creepersan.keyvalue.base.BaseViewHolder

class SettingGroupViewHolder(context:Context, parent:ViewGroup) : BaseViewHolder(context, R.layout.item_setting_group,parent) {

    val groupTextview by lazy { itemView.findViewById<TextView>(R.id.itemSettingGroupName) }

    fun setText(groupName:String){
        groupTextview.text = groupName
    }

}