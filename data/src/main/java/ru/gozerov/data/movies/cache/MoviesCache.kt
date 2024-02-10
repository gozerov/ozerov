package ru.gozerov.data.movies.cache

import kotlinx.coroutines.flow.Flow
import ru.gozerov.data.movies.cache.room.MovieDB
import ru.gozerov.domain.models.MovieCard

interface MoviesCache {

    suspend fun saveMovies(movies: List<MovieCard>)

    suspend fun getMovies(): Pair<String, List<MovieCard>>

    suspend fun getFavoriteMovies(): Pair<String, List<MovieCard>>

    suspend fun getMovieById(id: Int): MovieCard

    suspend fun updateMovie(movieCard: MovieCard): List<Pair<String, List<MovieCard>>>

    suspend fun searchMoviesByName(name: String): List<MovieCard>

}