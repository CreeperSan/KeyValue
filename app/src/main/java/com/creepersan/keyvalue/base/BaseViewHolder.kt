package com.creepersan.keyvalue.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

open class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    fun Context.getLayoutInflater():LayoutInflater{
        return LayoutInflater.from(this)
    }
    fun LayoutInflater.inflateView(view: Int, parent:ViewGroup? = null, attachToParent:Boolean = false):View{
        return this.inflate(view, parent, attachToParent)
    }


}