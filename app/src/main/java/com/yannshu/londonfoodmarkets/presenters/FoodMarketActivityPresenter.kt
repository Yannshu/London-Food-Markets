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
        displayDetails()
    }

    private fun displayDescription() {
        val description = market.description ?: mvpView?.getUnknownDescription()
        description?.let { mvpView?.displayDescription(it) }
    }

    private fun displayPhoto() {
        val photos = market.photos
        if (photos != null && !photos.isEmpty()) {
            mvpView?.displayPhoto(photos[FoodMarket.FIRST_PHOTO_INDEX])
        }
    }

    private fun displayAddress() {
        var formattedAddress: String? = null
        val address = market.address
        if (address != null) {
            val street = address.street
            val city = address.city
            val postcode = address.postcode

            if (street != null && city != null && postcode != null) {
                formattedAddress = mvpView?.getFormattedAddress(street, city, postcode)
            }
        }
        if (formattedAddress == null) {
            formattedAddress = mvpView?.getUnknownAddress()
        }
        formattedAddress?.let {
            mvpView?.displayAddress(it)
        }
    }

    private fun displayOpeningHours() {
        val openingTimes = market.openingTimes
        val formattedOpeningHours = if (openingTimes != null) {
            val todayOpeningTimes = openingTimes.find { it.day.equals(dayOfWeek) }

            val openingHour = todayOpeningTimes?.openingHour
            val closingHour = todayOpeningTimes?.closingHour

            if (openingHour != null && closingHour != null) {
                mvpView?.getFormattedOpeningHours(openingHour, closingHour)
            } else {
                mvpView?.getClosed()
            }
        } else {
            mvpView?.getUnknownOpeningHours()
        }
        formattedOpeningHours?.let {
            mvpView?.displayOpeningHours(it)
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

    private fun displayDetails() {
        val categories = market.categories
        if (categories != null && !categories.isEmpty()) {
            mvpView?.hideFarmersStalls()
            mvpView?.hideStreetFoodStands()
            categories.forEach {
                when (it) {
                    FoodMarket.CATEGORY_FARMERS_MARKET -> mvpView?.showFarmersStalls()
                    FoodMarket.CATEGORY_STREET_FOOD -> mvpView?.showStreetFoodStands()
                }
            }
        } else {
            mvpView?.hideDetails()
        }
    }
}