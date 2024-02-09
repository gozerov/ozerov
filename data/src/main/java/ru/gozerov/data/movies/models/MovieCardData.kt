package ru.gozerov.data.movies.models

import ru.gozerov.domain.models.MovieCard

data class MovieCardData(
    val filmId: Int,
    val nameRu: String,
    val year: String,
    val genres: List<Genre>,
    val posterUrlPreview: String,
)

fun MovieCardData.toMovieCard() =
    MovieCard(filmId, nameRu, year, genres.map { it.genre }, posterUrlPreview, false)