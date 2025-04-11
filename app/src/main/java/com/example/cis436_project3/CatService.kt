package com.example.cis436_project3

import android.content.Context
import android.util.Log
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.android.volley.Request
import com.android.volley.Response
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class CatService(private val context: Context) {
    private val apiKey = "live_TUoNAwuKAWoA9Taw4RdGOrs4XgMWbrYr1dgZHWKl8z470HLZ1stRNDM3gpm4TlFk"

    fun getBreedImage(breedId: String, onResponse: (String) -> Unit, onError: (String) -> Unit) {
        val url = "https://api.thecatapi.com/v1/images/search?breed_ids=$breedId&api_key=$apiKey"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                try {
                    // Parse the response
                    val jsonResponse = JSONArray(response)
                    val imageUrl = jsonResponse.getJSONObject(0).getString("url")

                    // Pass the image URL to onResponse callback
                    onResponse(imageUrl)
                } catch (e: JSONException) {
                    Log.e("CatService", "JSON error: ${e.message}")
                    onError("Error parsing the response.")
                }
            },
            { error ->
                Log.e("CatService", "Error: ${error.message}")
                onError("Failed to fetch data.")
            }
        )

        // Add the request to the request queue
        Volley.newRequestQueue(context).add(stringRequest)
    }

    fun getBreeds(onSuccess: (List<CatBreed>) -> Unit, onError: (String) -> Unit) {
        val catUrl = "https://api.thecatapi.com/v1/breeds?api_key=$apiKey"
        val queue = Volley.newRequestQueue(context)
        val stringRequest = StringRequest(
            Request.Method.GET, catUrl,
            { response ->
                val catsArray : JSONArray = JSONArray(response)
                val breedList = mutableListOf<CatBreed>()

                for(i in 0 until catsArray.length()){
                    val cat = catsArray.getJSONObject(i)
                    val breed = CatBreed(
                        id = cat.getString("id"),
                        name = cat.getString("name"),
                        temperament = cat.getString("temperament"),
                        origin = cat.getString("origin")
                    )
                    breedList.add(breed)
                }
                onSuccess(breedList)
            },
            {
                Log.i("CatService", "Error fetching data!")
                onError("Error parsing data")
            })
        queue.add(stringRequest)
    }
}