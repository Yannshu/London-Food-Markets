package com.yannshu.londonfoodmarkets.ui.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        retrieveIntentBundle(intent.extras)
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    protected open fun retrieveIntentBundle(extras: Bundle?) {
    }
}