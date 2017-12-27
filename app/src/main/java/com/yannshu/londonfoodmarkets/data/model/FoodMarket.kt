package com.yannshu.londonfoodmarkets.data.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.firebase.firestore.GeoPoint

class FoodMarket {

    @JsonProperty("name")
    var name: String? = null

    @JsonProperty("address")
    var address: Address? = null

    @JsonProperty("categories")
    var categories: List<String>? = null

    @JsonProperty("coordinates")
    var coordinates: GeoPoint? = null

    @JsonProperty("description")
    var description: String? = null

    @JsonProperty("opening_times")
    var openingTimes: List<OpeningTime>? = null

    @JsonProperty("website")
    var website: String? = null
}