package com.yannshu.londonfoodmarkets.data.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.firebase.firestore.GeoPoint
import com.yannshu.londonfoodmarkets.config.GeoPointParcelConverter
import org.parceler.Parcel
import org.parceler.ParcelClass
import org.parceler.ParcelConstructor

@Parcel
@ParcelClass(
        value = GeoPoint::class,
        annotation = Parcel(converter = GeoPointParcelConverter::class)
)
class FoodMarket() {

    companion object {
        val FIRST_PHOTO_INDEX = 0
        val CATEGORY_FARMERS_MARKET = "farmers-market"
        val CATEGORY_STREET_FOOD = "street-food"
    }

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

    @JsonProperty("photos")
    var photos: List<String>? = null

    @JsonProperty("size")
    var size: Int = 0

    @ParcelConstructor
    constructor(name: String?, address: Address?, categories: List<String>?, coordinates: GeoPoint?, description: String?, openingTimes: List<OpeningTime>?, website: String?, photos: List<String>?, size: Int) : this() {
        this.name = name
        this.address = address
        this.categories = categories
        this.coordinates = coordinates
        this.description = description
        this.openingTimes = openingTimes
        this.website = website
        this.photos = photos
        this.size = size
    }
}