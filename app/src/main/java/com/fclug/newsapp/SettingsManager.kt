package com.fclug.newsapp

import android.content.Context
import com.fclug.newsapp.data.NewsApi

const val PREF_NAME = "news_app"
const val KEY_COUNTRY = "country"

class SettingsManager(context: Context) {

    private val preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun getCountryOption(): String = preferences.getString(KEY_COUNTRY, NewsApi.DEFAULT_COUNTRY) ?: NewsApi.DEFAULT_COUNTRY

    fun saveCountryOption(country: String) {
        val editor = preferences.edit()
        editor.putString(KEY_COUNTRY, country)
        editor.apply()
    }
}