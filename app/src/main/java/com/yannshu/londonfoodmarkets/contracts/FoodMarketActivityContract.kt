package com.yannshu.londonfoodmarkets.contracts

interface FoodMarketActivityContract {
    interface View : MvpView {
        fun displayPhoto(url: String)
        fun displayAddress(address: String)
        fun getFormattedAddress(street: String, city: String, postcode: String): String
        fun getUnknownAddress(): String
        fun displayOpeningHours(openingHours: String)
        fun getFormattedOpeningHours(openingHour: String, closingHour: String): String
        fun getClosed(): String
    }
}