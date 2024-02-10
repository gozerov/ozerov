package ru.gozerov.data.movies.cache

import kotlinx.coroutines.flow.Flow
import ru.gozerov.domain.models.MovieCard

interface MoviesCache {

    suspend fun saveMovies(movies: List<MovieCard>)

    suspend fun getMovies() : Flow<MovieCard>

    suspend fun getMovieById(id: Int): MovieCard

}