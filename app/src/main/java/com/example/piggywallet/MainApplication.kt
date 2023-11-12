package com.example.piggywallet

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.piggywallet.module.authen.LoginActivity
import com.example.piggywallet.utils.FirebaseUtils

class MainApplication : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        Handler().postDelayed({

            checkUser()

        }, 1000)

    }

    private fun checkUser() {

        if(FirebaseUtils.firebaseUser != null){
            MainActivity.start(this)
        }
        if(FirebaseUtils.firebaseUser == null){
            LoginActivity.open(this)
        }

    }
}