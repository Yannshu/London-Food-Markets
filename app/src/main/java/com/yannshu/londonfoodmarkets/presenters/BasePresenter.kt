package com.yannshu.londonfoodmarkets.presenters

import com.yannshu.londonfoodmarkets.contracts.MvpView

open class BasePresenter<in T : MvpView> : Presenter<T> {

    private var mvpView: T? = null

    override fun attachView(mvpView: T) {
        this.mvpView = mvpView
    }

    override fun detachView() {
        mvpView = null
    }
}