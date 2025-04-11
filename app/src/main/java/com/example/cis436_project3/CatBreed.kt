package com.example.cis436_project3

data class CatBreed(
    val id: String,
    val name: String,
    val temperament: String?,
    val origin: String?
) {
    val details: String
        get() = "Temperament: $temperament\nOrigin: $origin"
}