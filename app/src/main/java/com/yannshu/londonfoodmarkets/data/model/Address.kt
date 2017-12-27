package com.yannshu.londonfoodmarkets.data.model

import com.fasterxml.jackson.annotation.JsonProperty

class Address {

    @JsonProperty("street")
    var street: String? = null

    @JsonProperty("city")
    var city: String? = null

    @JsonProperty("postcode")
    var postcode: String? = null
}