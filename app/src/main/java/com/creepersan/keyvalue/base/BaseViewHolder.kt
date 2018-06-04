package com.creepersan.keyvalue.base

import android.content.Context
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

open class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    constructor(context: Context,@LayoutRes layoutID:Int, parent: ViewGroup) : this(LayoutInflater.from(context).inflate(layoutID,parent,false))

    fun Context.getLayoutInflater():LayoutInflater{
        return LayoutInflater.from(this)
    }
    fun LayoutInflater.inflateView(view: Int, parent:ViewGroup? = null, attachToParent:Boolean = false):View{
        return this.inflate(view, parent, attachToParent)
    }


}