package com.creepersan.keyvalue.activity

import android.app.Activity
import android.os.Bundle
import com.creepersan.keyvalue.R
import com.creepersan.keyvalue.base.BaseActivity
import com.creepersan.keyvalue.util.FormatCheckUtil
import kotlinx.android.synthetic.main.activity_table_add.*

class TableAddActivity : BaseActivity() {

    override val layoutID: Int = R.layout.activity_table_add

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initButton()
    }

    private fun initButton(){
        tableAddBtn.setOnClickListener {
            val nameStr = tableAddName.text.toString().trim()
            val description = tableAddDescription.text.toString().trim()

            if (!FormatCheckUtil.checkEdittext(nameStr,minLength = 1)){
                toast(R.string.tableAddNameTooShortHint)
                return@setOnClickListener
            }

            getDatabaseManager().insertTable(nameStr,0,0,description)
            setResult(Activity.RESULT_OK)
            toast(R.string.operationSuccess)
            finish()
        }
    }

}