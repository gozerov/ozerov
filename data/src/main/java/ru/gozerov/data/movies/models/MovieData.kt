package ru.gozerov.data.movies.models

import ru.gozerov.domain.models.Movie

data class MovieData(
    val kinopoiskId: Int,
    val nameRu: String,
    val posterUrl: String,
    val description: String,
    val genres: List<Genre>,
    val countries: List<Country>
)

fun MovieData.toMovie() = Movie(
    kinopoiskId,
    posterUrl,
    nameRu,
    description,
    genres.map { it.genre },
    countries.map { it.country }
)