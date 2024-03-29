package ru.gozerov.data.movies.cache

import ru.gozerov.domain.models.MovieCard

interface MoviesCache {

    suspend fun saveMovies(movies: List<MovieCard>)

    suspend fun getTopMovies(): Pair<String, List<MovieCard>>

    suspend fun getFavoriteMovies(): Pair<String, List<MovieCard>>

    suspend fun getMovieById(id: Int): MovieCard

    suspend fun updateMovie(id: Int): List<Pair<String, List<MovieCard>>>

    suspend fun searchTopMoviesByName(name: String): List<MovieCard>

    suspend fun searchFavoriteMoviesByName(name: String): List<MovieCard>

}