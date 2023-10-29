package com.example.piggywallet.common.base

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import java.util.*
@SuppressLint("Registered")
open class BaseActivity : Activity() {

    private var savedInstanceState: Bundle? = null


    fun isSavedInstanceStateNotNull() = savedInstanceState != null

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun attachBaseContext(base: Context) {
//        super.attachBaseContext(LocalizeManager.initDefaultLocalize(base))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }


    companion object {
        const val LOADING_DIALOG_TAG_ACTIVITY = "BaseLoadingScreenDialogFragmentActivity"
    }
}