package ru.gozerov.presentation.screens.movie_list

import ru.gozerov.domain.models.MovieCard
import ru.gozerov.domain.models.MovieListData

sealed class MovieListState {

    class Empty : MovieListState()

    class SuccessMovies(
        val movieListData: List<Pair<String, List<MovieListData>>>
    ) : MovieListState()

    class SuccessUpdatedMovies(
        val categoryWithMovies: List<Pair<String, List<MovieCard>>>
    ) : MovieListState()

    class Error(
        val message: String
    ) : MovieListState()

}