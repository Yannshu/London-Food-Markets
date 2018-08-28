package com.yannshu.londonfoodmarkets.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

class VectorDescriptorFactory {
    companion object {
        fun fromVector(context: Context, @DrawableRes vectorResId: Int): BitmapDescriptor? {
            val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
            if (vectorDrawable != null) {
                vectorDrawable.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
                val bitmap = Bitmap.createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
                val canvas = Canvas(bitmap)
                vectorDrawable.draw(canvas)
                return BitmapDescriptorFactory.fromBitmap(bitmap)
            }
            return null
        }
    }
}