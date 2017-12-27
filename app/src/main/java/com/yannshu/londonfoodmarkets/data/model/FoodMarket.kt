package com.yannshu.londonfoodmarkets.data.model

import com.google.firebase.firestore.GeoPoint

class FoodMarket {

    var name: String? = null

    var address: Address? = null

    var categories: List<String>? = null

    var coordinates: GeoPoint? = null

    var description: String? = null

    var openingTimes: List<OpeningTime>? = null

    var website: String? = null
}