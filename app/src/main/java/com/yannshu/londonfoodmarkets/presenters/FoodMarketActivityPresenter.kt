package com.yannshu.londonfoodmarkets.presenters

import com.yannshu.londonfoodmarkets.contracts.FoodMarketActivityContract
import com.yannshu.londonfoodmarkets.data.model.FoodMarket
import timber.log.Timber

class FoodMarketActivityPresenter(private val market: FoodMarket) : BasePresenter<FoodMarketActivityContract.View>() {

    fun displayMarket() {
        Timber.d(market.name + " " + market.coordinates)
    }
}