package ru.gozerov.domain.models

data class Movie(
    val id: Int,
    val imageUrl: String,
    val name: String,
    val description: String,
    val genres: List<String>,
    val countries: List<String>,
    val isFavorite: Boolean
)
