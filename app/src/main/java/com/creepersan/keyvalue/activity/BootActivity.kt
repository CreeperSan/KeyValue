package com.creepersan.keyvalue.activity

import android.app.KeyguardManager
import android.content.Context
import android.hardware.fingerprint.FingerprintManager
import android.os.Bundle
import android.os.CancellationSignal
import android.widget.Toast
import com.creepersan.keyvalue.R
import com.creepersan.keyvalue.base.BaseActivity

class BootActivity : BaseActivity(){

    override val layoutID: Int = R.layout.activity_boot

    private val mFingerprintManager by lazy { getSystemService(Context.FINGERPRINT_SERVICE) as FingerprintManager }
    private val mKeyguardManagger by lazy { getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initFingerPrint()
    }

    private fun initFingerPrint(){
        if (mFingerprintManager.isHardwareDetected){
            if (mKeyguardManagger.isDeviceSecure){
                if (mFingerprintManager.hasEnrolledFingerprints()){
                    // todo 这里有待提高安全性　crypto　对象是加密提供应用唯一Key用的，后面需要加上
                    mFingerprintManager.authenticate(null, CancellationSignal(), 0, object : FingerprintManager.AuthenticationCallback(){
                        override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                            super.onAuthenticationError(errorCode, errString)
                            toast(R.string.bootFingerPrintAuthError)
                        }

                        override fun onAuthenticationSucceeded(result: FingerprintManager.AuthenticationResult?) {
                            super.onAuthenticationSucceeded(result)
                            toast(R.string.bootFingerPrintAuthSuccess)
                            toActivity(MainActivity::class.java, true)
                        }

                        override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence?) {
                            super.onAuthenticationHelp(helpCode, helpString)
                            log("Help => $helpCode : $helpString")
                            helpString?.apply { toast(this.toString()) }
                        }

                        override fun onAuthenticationFailed() {
                            super.onAuthenticationFailed()
                            toast(R.string.bootFingerPrintAuthFail)
                        }
                    }, null)
                }else{
                    toast(R.string.bootFingerprintNoFingerPrints, Toast.LENGTH_LONG)
                    finish()
                }
            }else{
                toast(R.string.bootFingerprintNotSecure, Toast.LENGTH_LONG)
                finish()
            }
        }else{
            toast(R.string.bootFingerprintNotDetectHint, Toast.LENGTH_LONG)
            finish()
        }
    }


}