package com.yannshu.londonfoodmarkets.presenters

import com.yannshu.londonfoodmarkets.contracts.MvpView

open class BasePresenterTest<V : MvpView, P : BasePresenter<V>> {

    protected lateinit var presenter: P

    protected lateinit var view: V
}