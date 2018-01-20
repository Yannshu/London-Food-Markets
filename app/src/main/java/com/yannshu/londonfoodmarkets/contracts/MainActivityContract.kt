package com.yannshu.londonfoodmarkets.contracts

import com.yannshu.londonfoodmarkets.data.model.FoodMarket

interface MainActivityContract {
    interface View : MvpView {
        fun moveMapCenterTo(lat: Double, lng: Double, zoom: Float)
        fun addMarket(market: FoodMarket)
        fun displayFoodMarketList(foodMarkets: List<FoodMarket>)
    }
}