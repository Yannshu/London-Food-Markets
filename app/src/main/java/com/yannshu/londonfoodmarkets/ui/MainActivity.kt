package com.yannshu.londonfoodmarkets.ui

import android.os.Bundle
import com.yannshu.londonfoodmarkets.R
import com.yannshu.londonfoodmarkets.di.activity.HasActivitySubComponentBuilders
import com.yannshu.londonfoodmarkets.ui.base.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun injectMembers(hasActivitySubComponentBuilders: HasActivitySubComponentBuilders) {
        (hasActivitySubComponentBuilders
                .getActivityComponentBuilder(MainActivity::class.java) as MainActivityComponent.Builder)
                .activityModule(MainActivityComponent.MainActivityModule(this))
                .build()
                .injectMembers(this)
    }
}
