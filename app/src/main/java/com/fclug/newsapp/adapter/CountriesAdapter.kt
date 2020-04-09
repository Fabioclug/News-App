package com.fclug.newsapp.adapter

import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.recyclerview.widget.RecyclerView
import com.fclug.newsapp.R
import com.fclug.newsapp.util.TAG
import kotlinx.android.synthetic.main.country_list_item.view.*

class CountriesAdapter(private var currentOption: String, private val onItemClick: (String) -> Unit, resources: Resources): RecyclerView.Adapter<CountriesAdapter.CountryViewHolder>() {

    private val countryNames = resources.getStringArray(R.array.country_entries)
    private val countryValues = resources.getStringArray(R.array.country_values)

    private var currentItem: RadioGroup? = null

    inner class CountryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(countryName: String, countryValue: String) {
            // clear before drawing
            itemView.country_item_selector_group?.clearCheck()

            // check if it is the current selected option
            if(currentOption == countryValue) {
                currentItem = itemView.country_item_selector_group
                itemView.country_item_selector.isChecked = true
            }

            itemView.country_item_name.text = countryName
            itemView.setOnClickListener {
                currentItem?.clearCheck()
                currentItem = itemView.country_item_selector_group
                itemView.country_item_selector.isChecked = true
                currentOption = countryValue
                onItemClick.invoke(countryValue)
                Log.d(TAG, "$countryName - $countryValue")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.country_list_item, parent, false)
        return CountryViewHolder(view)
    }

    override fun getItemCount() = countryNames.size

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(countryNames[position], countryValues[position])
    }
}