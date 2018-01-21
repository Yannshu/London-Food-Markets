package com.yannshu.londonfoodmarkets.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.view.MenuItem
import com.yannshu.londonfoodmarkets.R
import com.yannshu.londonfoodmarkets.di.activity.HasActivitySubComponentBuilders
import com.yannshu.londonfoodmarkets.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_about.toolbar

class AboutActivity : BaseActivity() {

    companion object {
        fun getStartingIntent(context: Context): Intent {
            return Intent(context, AboutActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        initToolbar()
    }

    override fun injectMembers(hasActivitySubComponentBuilders: HasActivitySubComponentBuilders) {
        (hasActivitySubComponentBuilders
                .getActivityComponentBuilder(AboutActivity::class.java) as AboutActivityComponent.Builder)
                .activityModule(AboutActivityComponent.AboutActivityModule(this))
                .build()
                .injectMembers(this)
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}