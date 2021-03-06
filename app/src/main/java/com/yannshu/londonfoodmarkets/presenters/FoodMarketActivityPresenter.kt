package com.yannshu.londonfoodmarkets.presenters

import com.yannshu.londonfoodmarkets.R
import com.yannshu.londonfoodmarkets.contracts.FoodMarketActivityContract
import com.yannshu.londonfoodmarkets.data.model.FoodMarket
import com.yannshu.londonfoodmarkets.data.model.OpeningTime
import com.yannshu.londonfoodmarkets.utils.TimeConstants

class FoodMarketActivityPresenter(private val market: FoodMarket, private val today: String) : BasePresenter<FoodMarketActivityContract.View>() {

    companion object {
        const val SIZE_SMALL = 20
        const val SIZE_MEDIUM = 50
        const val SIZE_BIG = 100
    }

    fun displayMarket() {
        displayDescription()
        displayPhoto()
        displayAddress()
        displayOpeningHours()
        displayWebsite()

        displayDetailsLayout()
        displaySize()
        displayCategories()
    }

    internal fun displayDescription() {
        val description = market.description ?: mvpView?.getUnknownDescription()
        description?.let { mvpView?.displayDescription(it) }
    }

    internal fun displayPhoto() {
        val photos = market.photos
        if (photos != null && !photos.isEmpty()) {
            mvpView?.displayPhoto(photos[FoodMarket.FIRST_PHOTO_INDEX])
        } else {
            mvpView?.displayPlaceholder()
        }
    }

    internal fun displayAddress() {
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

    internal fun displayOpeningHours() {
        val openingTimes = market.openingTimes

        getOpeningTimesForDay(openingTimes, today)?.let {
            mvpView?.displayOpeningHoursForToday(it)
        }
        mvpView?.highlightOpeningHoursDay(today)

        TimeConstants.DAYS_OF_WEEK.forEach { day ->
            getOpeningTimesForDay(openingTimes, day)?.let {
                mvpView?.displayOpeningHoursForDay(day, it)
            }
        }
    }

    internal fun getOpeningTimesForDay(openingTimes: List<OpeningTime>?, day: String): String? {
        return if (openingTimes != null) {
            val todayOpeningTimes = openingTimes.find { it.day.equals(day) }
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
    }

    internal fun displayWebsite() {
        val website = market.website
        if (website != null && website.isNotEmpty()) {
            mvpView?.displayWebsite(website)
        } else {
            mvpView?.hideWebsite()
        }
    }

    internal fun displayDetailsLayout() {
        val size = market.size
        val categories = market.categories

        if (size > 0 || (categories != null && !categories.isEmpty())) {
            mvpView?.showDetails()
        } else {
            mvpView?.hideDetails()
        }
    }

    internal fun displaySize() {
        val size = market.size

        if (size > 0) {
            mvpView?.showDetails()
            when {
                size < SIZE_SMALL -> mvpView?.showSize(R.string.size_small)
                size < SIZE_MEDIUM -> mvpView?.showSize(R.string.size_medium)
                size < SIZE_BIG -> mvpView?.showSize(R.string.size_big)
                else -> mvpView?.showSize(R.string.size_very_big)
            }
        } else {
            mvpView?.hideSize()
        }
    }

    internal fun displayCategories() {
        mvpView?.hideFarmersStalls()
        mvpView?.hideStreetFoodStands()

        val categories = market.categories
        if (categories != null && !categories.isEmpty()) {
            mvpView?.showDetails()

            categories.forEach {
                when (it) {
                    FoodMarket.CATEGORY_FARMERS -> mvpView?.showFarmersStalls()
                    FoodMarket.CATEGORY_STREET_FOOD -> mvpView?.showStreetFoodStands()
                }
            }
        }
    }
}