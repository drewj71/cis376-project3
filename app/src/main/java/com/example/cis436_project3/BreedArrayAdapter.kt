package com.example.cis436_project3

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

// Custom ArrayAdapter that only displays the name of the breed
class BreedArrayAdapter(context: Context, data: List<CatBreed>) :
    ArrayAdapter<CatBreed>(context, android.R.layout.simple_spinner_item, data) {

    // Override getView to display the breed name
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent) as TextView
        view.text = getItem(position)?.name // Display only the name
        return view
    }

    // Override getDropDownView to display the breed name in the dropdown
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent) as TextView
        view.text = getItem(position)?.name // Display only the name
        return view
    }
}
