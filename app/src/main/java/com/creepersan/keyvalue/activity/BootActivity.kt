package com.creepersan.keyvalue.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import com.creepersan.keyvalue.R
import com.creepersan.keyvalue.base.BaseActivity

class BootActivity : BaseActivity(){

    override val layoutID: Int = R.layout.activity_boot

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPermission()
        initActionBar()
        initFingerPrint()
    }

    private fun initPermission(){
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
        }else{
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
    private fun initActionBar(){
        supportActionBar?.hide()
    }
    private fun initFingerPrint(){
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0){
            finish()
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                startActivity(Intent(this, MainActivity::class.java))
            }else{
                toast(R.string.bootToastNoPermissionExiting)
            }
        }
    }


}