package com.yannshu.londonfoodmarkets.utils

import android.content.Context

class SharedPreferencesWrapper(context: Context, name: String = APP_SHARED_PREFERENCES) {

    companion object {
        private const val APP_SHARED_PREFERENCES = "london-food-markets"
    }

    private val sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)

    fun saveFloat(key: String, value: Float) = sharedPreferences.edit().putFloat(key, value).apply()

    fun getFloat(key: String, defaultValue: Float = 0.0f) = sharedPreferences.getFloat(key, defaultValue)

    fun saveLong(key: String, value: Long) = sharedPreferences.edit().putLong(key, value).apply()

    fun getLong(key: String, defaultValue: Long = 0L) = sharedPreferences.getLong(key, defaultValue)
}