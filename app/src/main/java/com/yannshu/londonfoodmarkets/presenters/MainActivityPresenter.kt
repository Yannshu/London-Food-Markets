package com.yannshu.londonfoodmarkets.presenters

import com.yannshu.londonfoodmarkets.contracts.MainActivityContract
import com.yannshu.londonfoodmarkets.data.FoodMarketsDataSource
import com.yannshu.londonfoodmarkets.data.model.FoodMarket

class MainActivityPresenter(private val foodMarketDataSource: FoodMarketsDataSource) : BasePresenter<MainActivityContract.View>() {

    companion object {
        private val LONDON_LAT = 51.507354
        private val LONDON_LNG = -0.127758
        private val DEFAULT_ZOOM = 10.0f
    }

    private var mapLoaded: Boolean = false

    private var foodMarkets: List<FoodMarket>? = null

    private val foodMarketListener = object : FoodMarketsDataSource.Listener {
        override fun onFailure() {
        }

        override fun onComplete(foodMarkets: List<FoodMarket>) {
            this@MainActivityPresenter.foodMarkets = foodMarkets
            if (mapLoaded && !foodMarkets.isEmpty()) {
                displayFoodMarkets(foodMarkets)
            }
        }
    }

    fun loadData() {
        foodMarketDataSource.listener = foodMarketListener
        foodMarketDataSource.loadFoodMarkets()
    }

    fun destroyData() {
        foodMarketDataSource.listener = null
        mapLoaded = false
    }

    fun onMapLoaded() {
        mapLoaded = true
        mvpView?.moveMapCenterTo(LONDON_LAT, LONDON_LNG, DEFAULT_ZOOM)
        val foodMarkets = this.foodMarkets
        if (foodMarkets != null && !foodMarkets.isEmpty()) {
            displayFoodMarkets(foodMarkets)
        }
    }

    private fun displayFoodMarkets(foodMarkets: List<FoodMarket>) {
        foodMarkets.forEach({ market: FoodMarket ->
            mvpView?.addMarket(market)
        })
    }
}