package com.yannshu.londonfoodmarkets.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import com.yannshu.londonfoodmarkets.R
import com.yannshu.londonfoodmarkets.data.model.Icon
import com.yannshu.londonfoodmarkets.extensions.safeStartActivityWithViewActionAndErrorDisplay
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

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initAuthorLayout() {
        authorLayout.setOnClickListener { safeStartActivityWithViewActionAndErrorDisplay(getString(R.string.author_website), rootLayout) }
    }

    private fun initIconsRecyclerView() {
        iconsAdapter.listener = object : IconsAdapter.Listener {
            override fun onClick(icon: Icon) {
                safeStartActivityWithViewActionAndErrorDisplay(getString(icon.url), rootLayout)
            }
        }
        iconsRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        iconsRecyclerView.adapter = iconsAdapter
    }
}