package com.yannshu.londonfoodmarkets.data.model

import com.fasterxml.jackson.annotation.JsonProperty
import org.parceler.Parcel
import org.parceler.ParcelConstructor

@Parcel
class OpeningTime() {

    @JsonProperty("day")
    var day: String? = null

    @JsonProperty("opening_hour")
    var openingHour: String? = null

    @JsonProperty("closing_hour")
    var closingHour: String? = null

    @ParcelConstructor
    constructor(day: String?, openingHour: String?, closingHour: String?) : this() {
        this.day = day
        this.openingHour = openingHour
        this.closingHour = closingHour
    }
}