package com.example.cis436_project3

import android.content.Context
import android.util.Log
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.android.volley.Request
import com.android.volley.Response
import org.json.JSONArray
import org.json.JSONObject

class CatService(private val context: Context) {
    private val apiKey = "live_TUoNAwuKAWoA9Taw4RdGOrs4XgMWbrYr1dgZHWKl8z470HLZ1stRNDM3gpm4TlFk"
    fun getBreeds(onSuccess: (List<String>) -> Unit, onError: (String) -> Unit) {
        val catUrl = "https://api.thecatapi.com/v1/breeds?api_key=$apiKey"
        val queue = Volley.newRequestQueue(context)
        val stringRequest = StringRequest(
            Request.Method.GET, catUrl,
            { response ->
                val catsArray : JSONArray = JSONArray(response)
                val breedNames = mutableListOf<String>()

                for(i in 0 until catsArray.length()) {
                    for (i in 0 until catsArray.length()) {
                        val cat = catsArray.getJSONObject(i)
                        val breedName = cat.getString("name")
                        breedNames.add(breedName)
                    }

                    onSuccess(breedNames)
                }
            },
            {
                Log.i("CatService", "Error fetching data!")
                onError("Error parsing data")
            })
        queue.add(stringRequest)
    }
}