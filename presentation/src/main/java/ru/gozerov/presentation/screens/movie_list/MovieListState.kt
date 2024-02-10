package ru.gozerov.presentation.screens.movie_list

import kotlinx.coroutines.flow.Flow
import ru.gozerov.domain.models.MovieCard

sealed class MovieListState {

    class Empty : MovieListState()

    class SuccessMovies(
        val categoryWithMovies: List<Pair<String, List<MovieCard>>>
    ) : MovieListState()

    class SuccessUpdatedMovies(
        val categoryWithMovies: List<Pair<String, List<MovieCard>>>
    ) : MovieListState()

    class Error(
        val message: String
    ) : MovieListState()

}