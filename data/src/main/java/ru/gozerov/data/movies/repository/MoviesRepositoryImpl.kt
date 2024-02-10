package ru.gozerov.data.movies.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import ru.gozerov.data.movies.cache.MoviesCache
import ru.gozerov.data.movies.models.toMovie
import ru.gozerov.data.movies.models.toMovieCard
import ru.gozerov.data.movies.remote.MoviesRemote
import ru.gozerov.domain.models.Movie
import ru.gozerov.domain.models.MovieCard
import ru.gozerov.domain.repositories.MoviesRepository
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val moviesRemote: MoviesRemote,
    private val moviesCache: MoviesCache
): MoviesRepository {

    override suspend fun getTopMovies(): Pair<String, List<MovieCard>> = withContext(Dispatchers.IO){
        val movies = moviesRemote.getTopMovies().map { it.toMovieCard() }
        moviesCache.saveMovies(movies)
        return@withContext moviesCache.getMovies()
    }

    override suspend fun getFavoriteMovies(): Pair<String, List<MovieCard>> = withContext(Dispatchers.IO) {
        return@withContext moviesCache.getFavoriteMovies()
    }

    override suspend fun getMovieById(id: Int): Movie = withContext(Dispatchers.IO) {
        moviesRemote.getMovieById(id).toMovie()
    }

    override suspend fun searchMovieByName(name: String): List<MovieCard> = withContext(Dispatchers.IO) {
        return@withContext moviesCache.searchMoviesByName(name)
    }

    override suspend fun setMovieFavorite(arg: Int): List<Pair<String, List<MovieCard>>> = withContext(Dispatchers.IO) {
        val movie = moviesCache.getMovieById(arg)
        return@withContext moviesCache.updateMovie(movie)
    }

}