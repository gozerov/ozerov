package ru.gozerov.data.movies.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.gozerov.data.movies.cache.MoviesCache
import ru.gozerov.data.movies.models.toMovie
import ru.gozerov.data.movies.models.toMovieCard
import ru.gozerov.data.movies.remote.MoviesRemote
import ru.gozerov.domain.models.Movie
import ru.gozerov.domain.models.MovieCard
import ru.gozerov.domain.models.NameWithCategory
import ru.gozerov.domain.repositories.MoviesRepository
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val moviesRemote: MoviesRemote,
    private val moviesCache: MoviesCache
) : MoviesRepository {

    override suspend fun getTopMovies(): Pair<String, List<MovieCard>> =
        withContext(Dispatchers.IO) {
            val cachedMovies = moviesCache.getTopMovies()
            if (cachedMovies.second.isEmpty()) {
                val movies = moviesRemote.getTopMovies().map { it.toMovieCard() }
                moviesCache.saveMovies(movies)
                return@withContext moviesCache.getTopMovies()
            } else return@withContext cachedMovies
        }

    override suspend fun getFavoriteMovies(): Pair<String, List<MovieCard>> =
        withContext(Dispatchers.IO) {
            val cachedMovies = moviesCache.getFavoriteMovies()
            if (cachedMovies.second.isEmpty()) {
                val movies = moviesRemote.getTopMovies().map { it.toMovieCard() }
                moviesCache.saveMovies(movies)
                return@withContext moviesCache.getFavoriteMovies()
            }
            else return@withContext cachedMovies
        }

    override suspend fun getMovieById(id: Int): Movie = withContext(Dispatchers.IO) {
        moviesRemote.getMovieById(id).toMovie()
    }

    override suspend fun searchMovieByNameAndCategory(data: NameWithCategory): List<MovieCard> =
        withContext(Dispatchers.IO) {
            return@withContext when (data.category) {
                0 -> moviesCache.searchTopMoviesByName(data.name)
                1 -> moviesCache.searchFavoriteMoviesByName(data.name)
                else -> throw IllegalArgumentException("Unknown category!")
            }
        }

    override suspend fun setMovieFavorite(arg: Int): List<Pair<String, List<MovieCard>>> =
        withContext(Dispatchers.IO) {
            val movie = moviesCache.getMovieById(arg)
            return@withContext moviesCache.updateMovie(movie)
        }

}