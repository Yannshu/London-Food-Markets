package com.yannshu.londonfoodmarkets.utils

import java.util.Calendar
import java.util.concurrent.TimeUnit

class MapCameraPositionSaver(private val sharedPreferencesWrapper: SharedPreferencesWrapper) {

    companion object {
        private const val LAST_MAP_CAMERA_POSITION_EXPIRE_TIME_DAYS = 7
        private const val KEY_LAST_TIME_SAVED = "last_time_saved"
        private const val KEY_LATITUDE = "latitude"
        private const val KEY_LONGITUDE = "longitude"
        private const val KEY_ZOOM = "zoom"
        private const val KEY_BEARING = "bearing"
        private const val KEY_TILT = "tilt"
    }

    fun saveMapCenterPosition(latitude: Float, longitude: Float, zoom: Float, bearing: Float, tilt: Float) {
        sharedPreferencesWrapper.saveLong(KEY_LAST_TIME_SAVED, Calendar.getInstance().timeInMillis)
        sharedPreferencesWrapper.saveFloat(KEY_LATITUDE, latitude)
        sharedPreferencesWrapper.saveFloat(KEY_LONGITUDE, longitude)
        sharedPreferencesWrapper.saveFloat(KEY_ZOOM, zoom)
        sharedPreferencesWrapper.saveFloat(KEY_BEARING, bearing)
        sharedPreferencesWrapper.saveFloat(KEY_TILT, tilt)
    }

    fun hasCameraPositionBeenSavedRecently(): Boolean {
        val lastTimeSaved = sharedPreferencesWrapper.getLong(KEY_LAST_TIME_SAVED)

        if (lastTimeSaved == 0L) {
            return false
        }
        return TimeUnit.MILLISECONDS.toDays(Calendar.getInstance().timeInMillis - lastTimeSaved) < LAST_MAP_CAMERA_POSITION_EXPIRE_TIME_DAYS
    }

    fun getLatitude() = sharedPreferencesWrapper.getFloat(KEY_LATITUDE)

    fun getLongitude() = sharedPreferencesWrapper.getFloat(KEY_LONGITUDE)

    fun getZoom() = sharedPreferencesWrapper.getFloat(KEY_ZOOM)

    fun getBearing() = sharedPreferencesWrapper.getFloat(KEY_BEARING)

    fun getTilt() = sharedPreferencesWrapper.getFloat(KEY_TILT)
}