package com.yannshu.londonfoodmarkets.contracts

interface FoodMarketActivityContract {
    interface View : MvpView {
        fun displayDescription(description: String)
        fun getUnknownDescription(): String
        fun displayPhoto(url: String)
        fun displayAddress(address: String)
        fun getFormattedAddress(street: String, city: String, postcode: String): String
        fun getUnknownAddress(): String
        fun displayOpeningHours(openingHours: String)
        fun getFormattedOpeningHours(openingHour: String, closingHour: String): String
        fun getClosed(): String
        fun displayWebsite(url: String)
        fun hideWebsite()
    }
}