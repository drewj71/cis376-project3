package com.example.cis436_project3

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TopViewModel : ViewModel() {
    private val _catBreeds = MutableLiveData<List<String>>()
    val catBreeds: LiveData<List<String>> = _catBreeds

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun setBreeds(breeds: List<String>) {
        _catBreeds.value = breeds
    }

    fun setError(msg: String) {
        _errorMessage.value = msg
    }
}
