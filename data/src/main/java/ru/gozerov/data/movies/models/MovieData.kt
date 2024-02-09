package ru.gozerov.data.movies.models

data class MovieData(
    val id: Int,
    val imageUrl: String,
    val name: String,
    val description: String,
    val genres: List<String>,
    val countries: List<String>,
    val isFavorite: Boolean
)