package com.yannshu.londonfoodmarkets.presenters

import com.yannshu.londonfoodmarkets.contracts.MainActivityContract
import com.yannshu.londonfoodmarkets.data.FoodMarketsDataSource

class MainActivityPresenter(private val foodMarketDataSource: FoodMarketsDataSource) : BasePresenter<MainActivityContract.View>() {

    fun loadData() {
        foodMarketDataSource.getFoodMarkets()
    }
}