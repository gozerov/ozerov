package ru.gozerov.data.movies.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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
            val movies = moviesRemote.getTopMovies().map { it.toMovieCard() }.toMutableList()
            val favoriteMovies = moviesCache.getFavoriteMovies().second
            favoriteMovies.forEach { favoriteCard ->
                val ind = movies.indexOfFirst { it.id == favoriteCard.id }
                movies.removeAt(ind)
                movies.add(ind, favoriteCard)
            }
            moviesCache.saveMovies(movies)
            return@withContext moviesCache.getTopMovies()
        }

    override suspend fun getFavoriteMovies(): Pair<String, List<MovieCard>> =
        withContext(Dispatchers.IO) {
            return@withContext moviesCache.getFavoriteMovies()
        }

    override suspend fun getMovieById(id: Int): Movie = withContext(Dispatchers.IO) {
        moviesRemote.getMovieById(id).toMovie()
    }

    override suspend fun searchMovieByNameAndCategory(data: NameWithCategory): List<MovieCard> =
        withContext(Dispatchers.IO) {
            return@withContext when (data.category) {
                0 -> {
                    val movies = moviesRemote.getTopMovies().map { it.toMovieCard() }
                    if (data.name.isEmpty())
                        return@withContext emptyList()
                    else
                        movies.filter { it.name.uppercase().contains(data.name.uppercase()) }
                }
                1 -> {
                    if (data.name.isEmpty())
                        return@withContext emptyList()
                    else
                        moviesCache.searchFavoriteMoviesByName(data.name)
                }
                else -> throw IllegalArgumentException("Unknown category!")
            }
        }

    override suspend fun setMovieFavorite(arg: Int): List<Pair<String, List<MovieCard>>> =
        withContext(Dispatchers.IO) {
            return@withContext moviesCache.updateMovie(arg)
        }

}