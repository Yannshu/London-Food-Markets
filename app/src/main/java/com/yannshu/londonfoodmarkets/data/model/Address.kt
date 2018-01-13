package com.yannshu.londonfoodmarkets.data.model

import com.fasterxml.jackson.annotation.JsonProperty
import org.parceler.Parcel
import org.parceler.ParcelConstructor

@Parcel
class Address() {

    @JsonProperty("street")
    var street: String? = null

    @JsonProperty("city")
    var city: String? = null

    @JsonProperty("postcode")
    var postcode: String? = null

    @ParcelConstructor
    constructor(street: String?, city: String?, postcode: String?) : this() {
        this.street = street
        this.city = city
        this.postcode = postcode
    }
}