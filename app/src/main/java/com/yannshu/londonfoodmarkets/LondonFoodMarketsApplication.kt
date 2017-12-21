package com.yannshu.londonfoodmarkets

import android.app.Application
import timber.log.Timber

class LondonFoodMarketsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        Timber.d("LondonFoodMarketsApplication.onCreate")
    }
}