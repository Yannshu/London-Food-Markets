package com.yannshu.londonfoodmarkets.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.NavUtils
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import com.yannshu.londonfoodmarkets.R
import com.yannshu.londonfoodmarkets.data.model.Icon
import com.yannshu.londonfoodmarkets.di.activity.HasActivitySubComponentBuilders
import com.yannshu.londonfoodmarkets.extensions.safeStartActivityWithViewAction
import com.yannshu.londonfoodmarkets.ui.adapters.IconsAdapter
import com.yannshu.londonfoodmarkets.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_about.authorLayout
import kotlinx.android.synthetic.main.activity_about.iconsRecyclerView
import kotlinx.android.synthetic.main.activity_about.rootLayout
import kotlinx.android.synthetic.main.activity_about.toolbar
import javax.inject.Inject

class AboutActivity : BaseActivity() {

    @Inject
    lateinit var iconsAdapter: IconsAdapter

    companion object {
        fun getStartingIntent(context: Context): Intent {
            return Intent(context, AboutActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        initToolbar()
        initAuthorLayout()
        initIconsRecyclerView()
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

    private fun initAuthorLayout() {
        authorLayout.setOnClickListener { openBrowser(getString(R.string.author_website)) }
    }

    private fun initIconsRecyclerView() {
        iconsAdapter.listener = object : IconsAdapter.Listener {
            override fun onClick(icon: Icon) {
                openBrowser(getString(icon.url))
            }
        }
        iconsRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        iconsRecyclerView.adapter = iconsAdapter
    }

    private fun openBrowser(url: String) {
        if (!safeStartActivityWithViewAction(url)) {
            Snackbar.make(rootLayout, R.string.install_browser, Snackbar.LENGTH_LONG).show()
        }
    }
}