package ru.gozerov.data.movies.cache

import ru.gozerov.data.movies.cache.room.MovieDB
import ru.gozerov.data.movies.cache.room.MovieDao
import ru.gozerov.data.movies.cache.room.toMovieCard
import ru.gozerov.domain.models.MovieCard
import javax.inject.Inject

class MoviesCacheImpl @Inject constructor(
    private val movieDao: MovieDao
) : MoviesCache {
    override suspend fun saveMovies(movies: List<MovieCard>) {
        movieDao.saveMovies(movies = movies.map { it.toMovieDB() })
    }

    override suspend fun getTopMovies(): Pair<String, List<MovieCard>> {
        return "Популярные" to movieDao.getTopMovies().map { it.toMovieCard() }
    }

    override suspend fun getFavoriteMovies(): Pair<String, List<MovieCard>> {
        return "Избранное" to movieDao.getFavoriteMovies().map { it.toMovieCard() }
    }

    override suspend fun getMovieById(id: Int): MovieCard {
        return movieDao.getMovieById(id).toMovieCard()
    }

    override suspend fun updateMovie(movieCard: MovieCard): List<Pair<String, List<MovieCard>>> {
        movieDao.setMovieFavorite(movieCard.toMovieDB().copy(isFavorite = !movieCard.isFavorite))
        return listOf(getTopMovies(), getFavoriteMovies())
    }

    override suspend fun searchTopMoviesByName(name: String): List<MovieCard> {
        return movieDao.searchMovies(name).map { it.toMovieCard() }
    }

    override suspend fun searchFavoriteMoviesByName(name: String): List<MovieCard> {
        return movieDao.searchMovies(name).filter { it.isFavorite }.map { it.toMovieCard() }
    }

    companion object {
        fun MovieCard.toMovieDB() =
            MovieDB(id, name, year, genres.joinToString(";"), imageUrl, isFavorite)
    }

}