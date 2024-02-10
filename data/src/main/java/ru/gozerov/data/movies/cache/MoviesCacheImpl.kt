package ru.gozerov.data.movies.cache

import kotlinx.coroutines.flow.Flow
import ru.gozerov.data.movies.cache.room.MovieDao
import ru.gozerov.domain.models.MovieCard
import javax.inject.Inject

class MoviesCacheImpl @Inject constructor(
    private val movieDao: MovieDao
) : MoviesCache {
    override suspend fun saveMovies(movies: List<MovieCard>) {
        TODO("Not yet implemented")
    }

    override suspend fun getMovies(): Flow<MovieCard> {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieById(id: Int): MovieCard {
        TODO("Not yet implemented")
    }
}