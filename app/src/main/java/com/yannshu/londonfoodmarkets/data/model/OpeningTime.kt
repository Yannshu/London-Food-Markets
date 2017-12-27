package com.yannshu.londonfoodmarkets.data.model

import com.fasterxml.jackson.annotation.JsonProperty

class OpeningTime {

    @JsonProperty("day")
    var day: String? = null

    @JsonProperty("opening_hour")
    var openingHour: String? = null

    @JsonProperty("closing_hour")
    var closingHour: String? = null
}