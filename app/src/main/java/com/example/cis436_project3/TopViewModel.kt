package com.example.cis436_project3

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TopViewModel : ViewModel() {
    private val _catBreeds = MutableLiveData<List<CatBreed>>()
    val catBreeds: LiveData<List<CatBreed>> = _catBreeds

    private val _selectedBreed = MutableLiveData<CatBreed>()
    val selectedBreed: LiveData<CatBreed> = _selectedBreed

    fun selectBreed(breed: CatBreed){
        _selectedBreed.value = breed
    }

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun setBreeds(breeds: List<CatBreed>) {
        _catBreeds.value = breeds
    }

    fun setError(msg: String) {
        _errorMessage.value = msg
    }
}
