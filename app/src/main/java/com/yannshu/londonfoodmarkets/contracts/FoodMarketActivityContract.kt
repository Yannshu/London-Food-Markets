package com.yannshu.londonfoodmarkets.contracts

interface FoodMarketActivityContract {
    interface View : MvpView {
        fun displayPhoto(url: String)
    }
}