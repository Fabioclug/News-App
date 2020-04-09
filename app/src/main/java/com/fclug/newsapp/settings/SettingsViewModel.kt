package com.fclug.newsapp.settings

import androidx.lifecycle.ViewModel
import com.fclug.newsapp.SettingsManager
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class SettingsViewModel: ViewModel(), KoinComponent {
    private val settingsManager: SettingsManager by inject()

    val currentCountryOption
        get() = settingsManager.getCountryOption()

    fun saveCountryOption(country: String) {
        settingsManager.saveCountryOption(country)
    }
}