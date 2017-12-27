package com.yannshu.londonfoodmarkets.presenters

import com.yannshu.londonfoodmarkets.contracts.MvpView

interface Presenter<in V : MvpView> {
    fun attachView(mvpView: V)
    fun detachView()
}