package com.yannshu.londonfoodmarkets.ui

import android.os.Bundle
import com.yannshu.londonfoodmarkets.R
import com.yannshu.londonfoodmarkets.contracts.MainActivityContract
import com.yannshu.londonfoodmarkets.di.activity.HasActivitySubComponentBuilders
import com.yannshu.londonfoodmarkets.presenters.MainActivityPresenter
import com.yannshu.londonfoodmarkets.ui.base.BaseActivity
import javax.inject.Inject

class MainActivity : BaseActivity(), MainActivityContract.View {

    @Inject
    internal lateinit var presenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.attachView(this)
    }

    override fun onResume() {
        super.onResume()
        presenter.present()
    }

    override fun injectMembers(hasActivitySubComponentBuilders: HasActivitySubComponentBuilders) {
        (hasActivitySubComponentBuilders
                .getActivityComponentBuilder(MainActivity::class.java) as MainActivityComponent.Builder)
                .activityModule(MainActivityComponent.MainActivityModule(this))
                .build()
                .injectMembers(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}
