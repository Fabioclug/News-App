package com.fclug.newsapp.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import com.fclug.newsapp.R
import com.fclug.newsapp.adapter.CountriesAdapter
import kotlinx.android.synthetic.main.fragment_settings.*
import org.koin.android.ext.android.inject

class SettingsFragment : Fragment() {

    private val settingsViewModel: SettingsViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCountryList()
        findNavController().previousBackStackEntry?.savedStateHandle?.set("refresh", false)
    }

    private fun setupCountryList() {
        countryOptionsList.adapter = CountriesAdapter(settingsViewModel.currentCountryOption ,::saveCountryOption, resources)
    }

    private fun saveCountryOption(country: String) {
        settingsViewModel.saveCountryOption(country)
        findNavController().previousBackStackEntry?.savedStateHandle?.set("refresh", true)
    }
}
