package com.yannshu.londonfoodmarkets.presenters

import com.yannshu.londonfoodmarkets.contracts.MainActivityContract
import com.yannshu.londonfoodmarkets.data.FoodMarketsDataSource
import com.yannshu.londonfoodmarkets.data.model.FoodMarket
import timber.log.Timber

class MainActivityPresenter(private val foodMarketDataSource: FoodMarketsDataSource) : BasePresenter<MainActivityContract.View>() {

    private val foodMarketListener = object : FoodMarketsDataSource.Listener {
        override fun onFailure() {
            Timber.e("Failed to load food markets")
        }

        override fun onComplete(foodMarkets: List<FoodMarket>) {
            Timber.d("Food markets loaded: " + foodMarkets.size)
        }
    }

    fun loadData() {
        foodMarketDataSource.listener = foodMarketListener
        foodMarketDataSource.loadFoodMarkets()
    }

    fun destroyData() {
        foodMarketDataSource.listener = null
    }

    fun onMapLoaded() {
    }
}