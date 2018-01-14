package com.yannshu.londonfoodmarkets.presenters

import com.yannshu.londonfoodmarkets.contracts.FoodMarketActivityContract
import com.yannshu.londonfoodmarkets.data.model.FoodMarket

class FoodMarketActivityPresenter(private val market: FoodMarket) : BasePresenter<FoodMarketActivityContract.View>() {

    companion object {
        val FIRST_PHOTO_INDEX = 0
    }

    fun displayMarket() {
        val photos = market.photos
        if (photos != null && !photos.isEmpty()) {
            mvpView?.displayPhoto(photos[FIRST_PHOTO_INDEX])
        }
    }
}