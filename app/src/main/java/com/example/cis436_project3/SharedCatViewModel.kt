package com.example.cis436_project3

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedCatViewModel : ViewModel() {

    private val _breeds = MutableLiveData<List<CatBreed>>()
    val breeds: LiveData<List<CatBreed>> = _breeds

    private val _selectedBreed = MutableLiveData<CatBreed?>()
    val selectedBreed: LiveData<CatBreed?> = _selectedBreed

    fun setBreeds(breeds: List<CatBreed>) {
        _breeds.value = breeds
    }

    fun setSelectedBreed(breed: CatBreed?) {
        _selectedBreed.value = breed
    }
}
