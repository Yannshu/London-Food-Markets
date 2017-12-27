package com.yannshu.londonfoodmarkets.presenters

import com.yannshu.londonfoodmarkets.contracts.MainActivityContract
import timber.log.Timber

class MainActivityPresenter : BasePresenter<MainActivityContract.View>() {
    fun present() {
        Timber.d("MainActivityPresenter")
    }
}