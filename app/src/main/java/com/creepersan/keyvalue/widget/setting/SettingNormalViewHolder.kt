package com.creepersan.keyvalue.widget.setting

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.creepersan.keyvalue.R

class SettingNormalViewHolder(context: Context, parent: ViewGroup)
    : RecyclerView.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_setting_normal, parent, false)) {

    val iconView by lazy { itemView.findViewById<ImageView>(R.id.itemSettingNormalIcon) }
    val titleView by lazy { itemView.findViewById<TextView>(R.id.itemSettingNormalTitle) }
    val descriptionView by lazy { itemView.findViewById<TextView>(R.id.itemSettingNormalDescription) }

    fun setData(image:Int, title:String, description:String){
        iconView.setImageResource(image)
        titleView.text = title
        descriptionView.text = description
    }

}