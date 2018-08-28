package com.yannshu.londonfoodmarkets.ui

import android.support.v7.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onResume() {
        super.onResume()
        startActivity(MainActivity.getStartingIntent(this))
        finish()
    }
}