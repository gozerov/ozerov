package ru.gozerov.domain.models

data class MovieCard(
    val id: Int,
    val name: String,
    val year: String,
    val genres: List<String>,
    val imageUrl: String,
    val isFavorite: Boolean
)
