package ru.gozerov.presentation.screens.movie_list

sealed class MovieListIntent {

    data object LoadMovies : MovieListIntent()

    data class UpdateMovieByFavorite(
        val id: Int
    ) : MovieListIntent()


}
