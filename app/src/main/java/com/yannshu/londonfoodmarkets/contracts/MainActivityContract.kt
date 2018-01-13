package com.yannshu.londonfoodmarkets.contracts

interface MainActivityContract {
    interface View : MvpView {
        fun moveMapCenterTo(lat: Double, lng: Double, zoom: Float)
    }
}