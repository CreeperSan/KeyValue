package com.creepersan.keyvalue.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import com.creepersan.keyvalue.R
import com.creepersan.keyvalue.base.BaseActivity
import com.creepersan.keyvalue.util.FormatCheckUtil
import com.creepersan.keyvalue.util.IconUtil
import kotlinx.android.synthetic.main.activity_key_value_add.*

class KeyValueAddActivity : BaseActivity() {
    companion object {
        const val KEY_INTENT_TABLE_ID = "table_id"

        const val VAL_DEFAULT_INTENT_TABLE_ID = -1

        const val REQUEST_CODE_PICK_ICON = 0
    }

    private var mTableID = VAL_DEFAULT_INTENT_TABLE_ID
    private var mIcon = IconPickActivity.VAL_INTENT_ICON_DEFAULT

    override val layoutID: Int = R.layout.activity_key_value_add

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActionBar()
        initIntent()
        initButton()
        initIcon()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_tool, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.menuToolRandomStrGenerator -> {
                toActivity(RandomGenerateActivity::class.java)
            }
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initActionBar(){
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    private fun initIntent(){
        mTableID = intent.getIntExtra(KEY_INTENT_TABLE_ID, -1)
        if (mTableID == VAL_DEFAULT_INTENT_TABLE_ID){
            toast(R.string.keyValueAddHintNoTableID)
            Handler().postDelayed({
                finish()
            },500)
        }
    }
    private fun initButton(){
        keyValueAddButton.setOnClickListener {
            val keyStr = keyValueAddKey.text.toString().trim()
            val valStr = keyValueAddValue.text.toString().trim()
            val desStr = keyValueAddDescription.text.toString().trim()

            if (!FormatCheckUtil.checkEdittext(keyStr,minLength = 1)){
                toast(R.string.keyValueAddKeyFormatError)
                return@setOnClickListener
            }

            if (!FormatCheckUtil.checkEdittext(valStr,minLength = 1)){
                toast(R.string.keyValueAddValueFormatError)
                return@setOnClickListener
            }

            getDatabaseManager().insertKeyValue(keyStr,0,mIcon,valStr,mTableID,desStr)
            toast(R.string.keyValueAddHintSuccess)
            setResult(Activity.RESULT_OK)
            finish()
        }
    }
    private fun initIcon(){
        keyValueAddIconLayout.setOnClickListener {
            startActivityForResult(Intent(this@KeyValueAddActivity, IconPickActivity::class.java), REQUEST_CODE_PICK_ICON)
        }
        keyValueAddIconIcon.setImageResource(IconUtil.getIcon(mIcon))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            REQUEST_CODE_PICK_ICON -> {
                if (resultCode == Activity.RESULT_OK){
                    mIcon = data?.getIntExtra(IconPickActivity.KEY_INTENT_ICON, mIcon) ?: mIcon
                    keyValueAddIconIcon.setImageResource(IconUtil.getIcon(mIcon))
                }
            }
        }
    }



}