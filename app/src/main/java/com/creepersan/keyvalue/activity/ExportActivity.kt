package com.creepersan.keyvalue.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import com.creepersan.keyvalue.App
import com.creepersan.keyvalue.R
import com.creepersan.keyvalue.base.BaseActivity
import kotlinx.android.synthetic.main.activity_export.*

class ExportActivity : BaseActivity() {

    override val layoutID: Int = R.layout.activity_export
    private val tableList by lazy { ArrayList<TableBean>() }
    private val tableAdapter by lazy { TableAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initListView()
        initData()
    }

    /* HERE IS FOR INIT */
    private fun initListView(){
        exportTableList.layoutManager = LinearLayoutManager(this)
        exportTableList.adapter = tableAdapter
    }
    private fun initData(){
        tableList.clear()
        App.getDatabaseManger().getAllTableList().forEach {
            tableList.add(TableBean(it.name, false))
        }
    }

    /* HERE IS INNER CLASS */
    private inner class TableBean(var tableName:String, var isCheck:Boolean)
    private inner class TableViewHolder(parent:ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(this).inflate(R.layout.item_export_table,parent,false)){

        val checkBox by lazy { itemView.findViewById<CheckBox>(R.id.itemExportCheckbox) }
        val textView by lazy { itemView.findViewById<TextView>(R.id.itemExportTextView) }


        fun setText(content:String){
            textView.text = content
        }
        fun setCheck(isCheck:Boolean){
            checkBox.isChecked = isCheck
        }

    }
    private inner class TableAdapter : RecyclerView.Adapter<TableViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TableViewHolder {
            return TableViewHolder(parent)
        }

        override fun getItemCount(): Int {
            return tableList.size
        }

        override fun onBindViewHolder(holder: TableViewHolder, position: Int) {
            val item = tableList[position]
            holder.setCheck(item.isCheck)
            holder.setText(item.tableName)
            holder.itemView.setOnClickListener {
                item.isCheck = !item.isCheck
                tableAdapter.notifyItemChanged(holder.adapterPosition)
            }
        }

    }
}