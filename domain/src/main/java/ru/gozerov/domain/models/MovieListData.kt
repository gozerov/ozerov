package ru.gozerov.domain.models

sealed class MovieListData(
    val tabName: String
) {

    data class MovieList(
        val title: String,
        val movies: List<MovieCard>
    ) : MovieListData(title)

    data class ErrorMovie(
        val title: String
    ) : MovieListData(title)

}
