package com.yannshu.londonfoodmarkets.utils

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil

class MapsUtils {
    fun computeDistance(lat1: Double, lng1: Double, lat2: Double, lng2: Double) =
        SphericalUtil.computeDistanceBetween(LatLng(lat1, lng1), LatLng(lat2, lng2))
}