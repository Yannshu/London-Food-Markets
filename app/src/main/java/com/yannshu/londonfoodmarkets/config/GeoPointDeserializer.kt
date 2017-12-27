package com.yannshu.londonfoodmarkets.config

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.google.firebase.firestore.GeoPoint

class GeoPointDeserializer : StdDeserializer<GeoPoint>(GeoPoint::class.java) {

    companion object {
        val ATTRIBUTE_LATITUDE = "latitude"
        val ATTRIBUTE_LONGITUDE = "longitude"
    }

    override fun deserialize(jsonParser: JsonParser?, context: DeserializationContext?): GeoPoint {
        val node = jsonParser?.codec?.readTree<JsonNode>(jsonParser)
        val latitude = node?.get(ATTRIBUTE_LATITUDE)?.asDouble()
        val longitude = node?.get(ATTRIBUTE_LONGITUDE)?.asDouble()
        return GeoPoint(latitude!!, longitude!!)
    }
}
