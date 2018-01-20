package com.yannshu.londonfoodmarkets.presenters

import com.yannshu.londonfoodmarkets.contracts.MainActivityContract
import com.yannshu.londonfoodmarkets.data.FoodMarketsDataSource
import com.yannshu.londonfoodmarkets.data.model.FoodMarket
import com.yannshu.londonfoodmarkets.utils.MapsUtils

class MainActivityPresenter(private val foodMarketDataSource: FoodMarketsDataSource, private val mapsUtils: MapsUtils) :
        BasePresenter<MainActivityContract.View>() {

    companion object {
        private val LONDON_LAT = 51.507354
        private val LONDON_LNG = -0.127758
        private val DEFAULT_ZOOM = 10.0f
    }

    private var mapLoaded: Boolean = false

    private var foodMarkets: List<FoodMarket>? = null

    private var userLat: Double = LONDON_LAT

    private var userLng: Double = LONDON_LNG

    private val foodMarketListener = object : FoodMarketsDataSource.Listener {
        override fun onFailure() {
        }

        override fun onComplete(foodMarkets: List<FoodMarket>) {
            this@MainActivityPresenter.foodMarkets = sortFoodMarketByDistanceTo(foodMarkets, userLat, userLng)
            if (mapLoaded) {
                this@MainActivityPresenter.foodMarkets?.let {
                    displayFoodMarkets(it)
                }
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
        foodMarkets?.let {
            displayFoodMarkets(it)
        }
    }

    private fun displayFoodMarkets(foodMarkets: List<FoodMarket>) {
        foodMarkets.forEach({ market: FoodMarket ->
            mvpView?.addMarket(market)
        })
        mvpView?.displayFoodMarketList(foodMarkets)
    }

    fun onLocationLoaded(latitude: Double, longitude: Double) {
        userLat = latitude
        userLng = longitude
        foodMarkets?.let {
            val sortedFoodMarkets = sortFoodMarketByDistanceTo(it, userLat, userLng)
            mvpView?.displayFoodMarketList(sortedFoodMarkets)
            foodMarkets = sortedFoodMarkets
        }
    }

    private fun sortFoodMarketByDistanceTo(foodMarkets: List<FoodMarket>, latitude: Double, longitude: Double): List<FoodMarket> {
        return ArrayList(foodMarkets.sortedWith(compareBy({
            mapsUtils.computeDistance(it.coordinates!!.latitude, it.coordinates!!.longitude, latitude, longitude)
        })))
    }
}