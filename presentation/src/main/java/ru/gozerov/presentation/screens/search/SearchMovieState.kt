package ru.gozerov.presentation.screens.search

import ru.gozerov.domain.models.MovieCard

sealed class SearchMovieState {

    data object Empty : SearchMovieState()

    data class SearchedMovies(
        val movies: List<MovieCard>
    ) : SearchMovieState()

    data class Error(
        val message: String
    ) : SearchMovieState()

}
