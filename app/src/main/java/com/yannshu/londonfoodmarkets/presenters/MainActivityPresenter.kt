package com.yannshu.londonfoodmarkets.presenters

import com.yannshu.londonfoodmarkets.R
import com.yannshu.londonfoodmarkets.contracts.MainActivityContract
import com.yannshu.londonfoodmarkets.data.FoodMarketsDataSource
import com.yannshu.londonfoodmarkets.data.model.FoodMarket
import com.yannshu.londonfoodmarkets.utils.MapCameraPositionSaver
import com.yannshu.londonfoodmarkets.utils.MapsUtils

class MainActivityPresenter(private val foodMarketDataSource: FoodMarketsDataSource,
    private val mapsUtils: MapsUtils,
    private val mapCameraPositionSaver: MapCameraPositionSaver,
    private val today: String) :
    BasePresenter<MainActivityContract.View>() {

    companion object {
        internal const val LONDON_LAT = 51.480000
        internal const val LONDON_LNG = -0.127758
        internal const val DEFAULT_ZOOM = 11.0f
    }

    internal var foodMarkets: List<FoodMarket>? = null

    private var userLat: Double = LONDON_LAT

    private var userLng: Double = LONDON_LNG

    private val foodMarketListener = object : FoodMarketsDataSource.Listener {
        override fun onFailure() {
        }

        override fun onComplete(foodMarkets: List<FoodMarket>) {
            this@MainActivityPresenter.foodMarkets = sortFoodMarketByDistanceTo(foodMarkets, userLat, userLng)
            this@MainActivityPresenter.foodMarkets?.let {
                mvpView?.setOpenTodayEnabled(true)
                displayFoodMarkets(it)
            }
        }
    }

    fun loadData() {
        foodMarketDataSource.listener = foodMarketListener
        foodMarketDataSource.loadFoodMarkets()
    }

    fun saveMapCameraPosition(latitude: Float, longitude: Float, zoom: Float, bearing: Float, tilt: Float) {
        mapCameraPositionSaver.saveMapCenterPosition(latitude, longitude, zoom, bearing, tilt)
    }

    fun destroyData() {
        foodMarketDataSource.listener = null
    }

    fun positionMapCenter() {
        if (mapCameraPositionSaver.hasCameraPositionBeenSavedRecently()) {
            mvpView?.moveMapCenterTo(mapCameraPositionSaver.getLatitude().toDouble(), mapCameraPositionSaver.getLongitude().toDouble(),
                mapCameraPositionSaver.getZoom(), mapCameraPositionSaver.getBearing(), mapCameraPositionSaver.getTilt())
        } else {
            mvpView?.moveMapCenterTo(LONDON_LAT, LONDON_LNG, DEFAULT_ZOOM)
        }
    }

    internal fun displayFoodMarkets(foodMarkets: List<FoodMarket>?) {
        foodMarkets?.let {
            mvpView?.clearMarkets()
            it.forEach { market: FoodMarket ->

                market.categories?.let { categories ->
                    if (categories.size == 1) {
                        market.drawableRes = when (categories[0]) {
                            FoodMarket.CATEGORY_FARMERS -> R.drawable.ic_farmer_marker
                            FoodMarket.CATEGORY_STREET_FOOD -> R.drawable.ic_street_food_marker
                            else -> R.drawable.ic_market_marker
                        }
                    }
                }
                mvpView?.addMarket(market)
            }
            mvpView?.renderMarkets()
            mvpView?.displayFoodMarketsRecyclerView(it)
        }
    }

    fun onLocationLoaded(latitude: Double, longitude: Double) {
        userLat = latitude
        userLng = longitude
        foodMarkets?.let {
            val sortedFoodMarkets = sortFoodMarketByDistanceTo(it, userLat, userLng)
            mvpView?.displayFoodMarketsRecyclerView(sortedFoodMarkets)
            foodMarkets = sortedFoodMarkets
        }
    }

    internal fun sortFoodMarketByDistanceTo(foodMarkets: List<FoodMarket>, latitude: Double, longitude: Double) =
        ArrayList(foodMarkets.sortedWith(compareBy {
            mapsUtils.computeDistance(it.coordinates!!.latitude, it.coordinates!!.longitude, latitude, longitude)
        }))

    fun filterMarkets(openToday: Boolean) {
        val filteredFoodMarkets = if (openToday) {
            foodMarkets?.filter { it.openingTimes?.find { it.day == today } != null }
        } else {
            foodMarkets
        }
        displayFoodMarkets(filteredFoodMarkets)
    }

    fun foodMarketsLoaded() = foodMarkets != null
}