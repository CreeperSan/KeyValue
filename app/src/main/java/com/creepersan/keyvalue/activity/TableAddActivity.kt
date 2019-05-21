package com.creepersan.keyvalue.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.creepersan.keyvalue.R
import com.creepersan.keyvalue.base.BaseActivity
import com.creepersan.keyvalue.database.Table
import com.creepersan.keyvalue.util.FormatCheckUtil
import com.creepersan.keyvalue.util.IconUtil
import kotlinx.android.synthetic.main.activity_table_add.*

class TableAddActivity : BaseActivity() {

    companion object {
        private val REQUEST_CODE_PICK_ICON = 0
    }
    override val layoutID: Int = R.layout.activity_table_add

    private var mIcon = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initActionBar()
        initButton()
        initIcon()
    }

    /**
     *  初始化
     */

    private fun initActionBar(){
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    private fun initButton(){
        tableAddBtn.setOnClickListener {
            val nameStr = tableAddName.text.toString().trim()
            val description = tableAddDescription.text.toString().trim()

            if (!FormatCheckUtil.checkEditText(nameStr,minLength = 1)){
                toast(R.string.tableAddNameTooShortHint)
                return@setOnClickListener
            }

            getTableDao().insertTable(Table().apply {
                val currentTime = System.currentTimeMillis()
                title = nameStr
                subtitle = description
                icon = mIcon
                createTime = currentTime
                modifyTime = currentTime
                extra = ""
            })
            setResult(Activity.RESULT_OK)
            toast(R.string.operationSuccess)
            finish()
        }
    }
    private fun initIcon(){
        tableAddIconLayout.setOnClickListener {
            startActivityForResult(Intent(this@TableAddActivity, IconPickActivity::class.java), REQUEST_CODE_PICK_ICON)
        }
        tableAddIconIcon.setImageResource(IconUtil.getIcon(mIcon))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            REQUEST_CODE_PICK_ICON -> {
                if (resultCode == Activity.RESULT_OK){
                    mIcon = data?.getIntExtra(IconPickActivity.KEY_INTENT_ICON, mIcon) ?: mIcon
                    tableAddIconIcon.setImageResource(IconUtil.getIcon(mIcon))
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}