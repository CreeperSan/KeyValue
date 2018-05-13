package com.creepersan.keyvalue.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

open class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    constructor(context:Context,itemViewID:Int,parent:ViewGroup):this(LayoutInflater.from(context).inflate(itemViewID,parent,false))


}