package com.creepersan.keyvalue.activity

import android.os.Bundle
import android.view.MenuItem
import android.widget.SeekBar
import com.creepersan.keyvalue.R
import com.creepersan.keyvalue.base.BaseActivity
import kotlinx.android.synthetic.main.activity_random_generate.*
import java.util.*

class RandomGenerateActivity : BaseActivity() {

    companion object {
        const val CHAR_NORMAL = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
    }

    override val layoutID: Int = R.layout.activity_random_generate

    private var mGenerateText : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActionBar()
        initSeekBar()
        initButton()
    }


    private fun initActionBar(){
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    private fun initSeekBar(){
        randomGenerateSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                mGenerateText = generateText(progress)
                randomGenerateContent.text = mGenerateText
            }
        })
    }
    private fun initButton(){
        randomGenerateGenerateButton.setOnClickListener {
            mGenerateText = generateText(randomGenerateSeekBar.progress)
            randomGenerateContent.text = mGenerateText
        }
        randomGenerateConfirmButton.setOnClickListener {
            finish()
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


    private fun generateText(length:Int):String{
        val random = Random()
        val textBuilder = StringBuilder()
        for (i in 0..length){
            textBuilder.append(CHAR_NORMAL[random.nextInt(CHAR_NORMAL.length)])
        }
        return textBuilder.toString()
    }
}