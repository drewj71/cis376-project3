package com.example.cis436_project3

import com.example.cis436_project3.CatService
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TopViewModel : ViewModel() {
    // MutableLiveData to hold the list of cat breeds
    private val _catBreeds = MutableLiveData<List<String>>()
    val catBreeds: LiveData<List<String>> = _catBreeds

    // MutableLiveData to handle error messages
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val catService = CatService()  // CatService initialized here

    // Fetch cat breeds from the API
    fun fetchCatBreeds() {
        viewModelScope.launch {
            catService.getBreeds(
                onSuccess = { breeds ->
                    _catBreeds.value = breeds  // Set the fetched data
                },
                onError = { error ->
                    _errorMessage.value = error  // Set the error message
                }
            )
        }
    }
}