package com.cst3115.enterprise.assignment2

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("weather_prefs", Context.MODE_PRIVATE)

    companion object {
        const val KEY_CITY_NAME = "city_name"
    }

    fun saveCityName(cityName: String) {
        prefs.edit().putString(KEY_CITY_NAME, cityName).apply()
    }

    fun getCityName(): String? {
        return prefs.getString(KEY_CITY_NAME, null)
    }
}
