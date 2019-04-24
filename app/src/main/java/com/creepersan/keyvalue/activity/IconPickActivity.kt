package com.creepersan.keyvalue.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.ImageView
import com.creepersan.keyvalue.R
import com.creepersan.keyvalue.base.BaseActivity
import com.creepersan.keyvalue.base.BaseViewHolder
import com.creepersan.keyvalue.util.IconUtil
import kotlinx.android.synthetic.main.activity_icon_pick.*

class IconPickActivity : BaseActivity() {

    companion object {
        const val KEY_INTENT_ICON = "icon"
        const val VAL_INTENT_ICON_DEFAULT = 0
    }

    override val layoutID: Int = R.layout.activity_icon_pick

    private val iconAdapter by lazy { IconAdapter() }
    private val iconList = IconUtil.ICON_USER_ARRAY
    private var mCurrentIcon = VAL_INTENT_ICON_DEFAULT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActionBar()
        initList()
        initIntent()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_icon_pick, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menuIconSave -> {
                setResult(Activity.RESULT_OK, Intent().apply { putExtra(KEY_INTENT_ICON, mCurrentIcon) })
                finish()
            }
            android.R.id.home -> {
                setResult(Activity.RESULT_CANCELED, Intent().apply {})
                finish()
            }
        }
        return true
    }

    /**
     *  初始化
     */

    private fun initActionBar(){
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    private fun initList(){
        iconPickList.layoutManager = GridLayoutManager(this, 5)
        iconPickList.adapter = iconAdapter
    }
    private fun initIntent(){
        mCurrentIcon = intent.getIntExtra(KEY_INTENT_ICON, VAL_INTENT_ICON_DEFAULT)
    }



    /**
     *  内部类
     */
    private inner class IconHolder(parent:ViewGroup) : BaseViewHolder(this,R.layout.item_icon,parent){
        val icon : ImageView = itemView.findViewById(R.id.itemIconIcon)

        fun setIcon(iconID:Int){
            icon.setImageResource(iconID)
        }

        fun setActive(){
            itemView.setBackgroundColor(Color.GRAY)
        }

        fun setInactive(){
            itemView.background = null
        }
    }
    private inner class IconAdapter : RecyclerView.Adapter<IconHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconHolder = IconHolder(parent)
        override fun getItemCount(): Int = iconList.size
        override fun onBindViewHolder(holder: IconHolder, pos: Int) {
            val position = holder.adapterPosition
            val iconItem = iconList[position]
            holder.setIcon(IconUtil.getIcon(iconItem))
            if (mCurrentIcon == position){
                holder.setActive()
            }else{
                holder.setInactive()
            }

            holder.icon.setOnClickListener {
                if (mCurrentIcon != position){
                    iconAdapter.notifyItemChanged(position)
                    iconAdapter.notifyItemChanged(mCurrentIcon)
                    mCurrentIcon = position
                }
            }
        }

    }


}