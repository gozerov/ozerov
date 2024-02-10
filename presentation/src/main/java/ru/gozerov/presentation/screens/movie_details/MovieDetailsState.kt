package ru.gozerov.presentation.screens.movie_details

import ru.gozerov.domain.models.Movie

sealed class MovieDetailsState {

    data object Empty : MovieDetailsState()

    data class SuccessMovie(
        val movie: Movie
    ) : MovieDetailsState()

    data class Error(
        val message: String
    ) : MovieDetailsState()

}
