package ru.gozerov.data.movies.models

import ru.gozerov.domain.models.MovieCard

data class MovieCardData(
    val filmId: Int,
    val nameRu: String,
    val year: String,
    val genres: List<Genre>,
    val posterUrlPreview: String,
    val isFavorite: Boolean
)

fun MovieCardData.toMovieCard() =
    MovieCard(
        filmId,
        nameRu,
        year,
        genres.first().genre.replaceFirstChar { it.uppercaseChar() },
        posterUrlPreview,
        isFavorite
    )