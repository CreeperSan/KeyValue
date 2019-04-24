package com.creepersan.keyvalue.activity

import android.content.Intent
import android.os.Bundle
import com.creepersan.keyvalue.R
import com.creepersan.keyvalue.base.BaseActivity

class BootActivity : BaseActivity(){

    override val layoutID: Int = R.layout.activity_boot

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActionBar()
        initFingerPrint()
    }

    private fun initActionBar(){
        supportActionBar?.hide()
    }
    private fun initFingerPrint(){
        startActivity(Intent(this, MainActivity::class.java))
    }


}