package com.creepersan.keyvalue.activity

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import com.creepersan.keyvalue.R
import com.creepersan.keyvalue.base.BaseActivity
import com.creepersan.keyvalue.util.FormatCheckUtil
import kotlinx.android.synthetic.main.activity_key_value_add.*

class KeyValueAddActivity : BaseActivity() {
    companion object {
        const val KEY_INTENT_TABLE_ID = "table_id"

        const val VAL_DEFAULT_INTENT_TABLE_ID = -1
    }

    private var mTableID = VAL_DEFAULT_INTENT_TABLE_ID

    override val layoutID: Int = R.layout.activity_key_value_add

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initIntent()
        initButton()
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

            getDatabaseManager().insertKeyValue(keyStr,0,0,valStr,mTableID,desStr)
            toast(R.string.keyValueAddHintSuccess)
            setResult(Activity.RESULT_OK)
            finish()
        }
    }



}