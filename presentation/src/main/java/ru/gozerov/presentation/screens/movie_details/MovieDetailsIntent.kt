package ru.gozerov.presentation.screens.movie_details

sealed class MovieDetailsIntent {

    data class LoadMovieDetails(
        val id: Int
    ) : MovieDetailsIntent()

}