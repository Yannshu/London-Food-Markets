package com.yannshu.londonfoodmarkets.contracts

import android.support.annotation.DrawableRes
import com.yannshu.londonfoodmarkets.data.model.FoodMarket

interface MainActivityContract {
    interface View : MvpView {
        fun moveMapCenterTo(lat: Double, lng: Double, zoom: Float, bearing: Float = 0.0f, tilt: Float = 0.0f)
        fun addMarket(market: FoodMarket, @DrawableRes drawableRes: Int)
        fun displayFoodMarketsRecyclerView(foodMarkets: List<FoodMarket>)
        fun setOpenTodayEnabled(enabled: Boolean)
        fun clearMarkers()
    }
}