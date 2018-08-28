package com.yannshu.londonfoodmarkets.config

import android.os.Parcel
import com.google.firebase.firestore.GeoPoint
import org.parceler.ParcelConverter

class GeoPointParcelConverter : ParcelConverter<GeoPoint> {

    override fun fromParcel(parcel: Parcel?): GeoPoint {
        val latitude = parcel?.readDouble()
        val longitude = parcel?.readDouble()
        return GeoPoint(latitude!!, longitude!!)
    }

    override fun toParcel(input: GeoPoint?, parcel: Parcel?) {
        parcel?.writeDouble(input!!.latitude)
        parcel?.writeDouble(input!!.longitude)
    }
}