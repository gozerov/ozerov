package ru.gozerov.data.movies.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.gozerov.data.movies.models.toMovieCard
import ru.gozerov.data.movies.remote.MoviesRemote
import ru.gozerov.domain.models.Movie
import ru.gozerov.domain.models.MovieCard
import ru.gozerov.domain.repositories.MoviesRepository
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val moviesRemote: MoviesRemote
): MoviesRepository {

    override suspend fun getTopMovies(): List<MovieCard> = withContext(Dispatchers.IO){
        moviesRemote.getTopMovies().map { it.toMovieCard() }
    }

    override suspend fun getMovieById(id: Int): Movie {
        throw Exception()
    }
}