package com.yannshu.londonfoodmarkets.presenters

import com.yannshu.londonfoodmarkets.contracts.FoodMarketActivityContract
import com.yannshu.londonfoodmarkets.data.model.FoodMarket

class FoodMarketActivityPresenter(private val market: FoodMarket, private val dayOfWeek: String) : BasePresenter<FoodMarketActivityContract.View>() {

    fun displayMarket() {
        displayDescription()
        displayPhoto()
        displayAddress()
        displayOpeningHours()
        displayWebsite()
    }

    private fun displayDescription() {
        market.description?.let {
            mvpView?.displayDescription(it)
        }
    }

    private fun displayPhoto() {
        val photos = market.photos
        if (photos != null && !photos.isEmpty()) {
            mvpView?.displayPhoto(photos[FoodMarket.FIRST_PHOTO_INDEX])
        }
    }

    private fun displayAddress() {
        market.address?.let {
            val street = it.street
            val city = it.city
            val postcode = it.postcode

            val address: String? = if (street != null && city != null && postcode != null) {
                mvpView?.getFormattedAddress(street, city, postcode)
            } else {
                mvpView?.getUnknownAddress()
            }
            address?.let {
                mvpView?.displayAddress(it)
            }
        }
    }

    private fun displayOpeningHours() {
        val openingTimes = market.openingTimes
        if (openingTimes != null) {
            val todayOpeningTimes = openingTimes.find { it.day.equals(dayOfWeek) }

            val openingHour = todayOpeningTimes?.openingHour
            val closingHour = todayOpeningTimes?.closingHour

            val openingHours: String?
            openingHours = if (openingHour != null && closingHour != null) {
                mvpView?.getFormattedOpeningHours(openingHour, closingHour)
            } else {
                mvpView?.getClosed()
            }
            if (openingHours != null) {
                mvpView?.displayOpeningHours(openingHours)
            }
        }
    }

    private fun displayWebsite() {
        val website = market.website
        if (website != null) {
            mvpView?.displayWebsite(website)
        } else {
            mvpView?.hideWebsite()
        }
    }
}