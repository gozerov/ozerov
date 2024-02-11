package ru.gozerov.data.movies.cache

import ru.gozerov.data.movies.cache.room.entity.FavoriteMovieDB
import ru.gozerov.data.movies.cache.room.dao.FavoriteMovieDao
import ru.gozerov.data.movies.cache.room.entity.MovieDB
import ru.gozerov.data.movies.cache.room.dao.MovieDao
import ru.gozerov.data.movies.cache.room.entity.toMovieCard
import ru.gozerov.domain.models.MovieCard
import javax.inject.Inject

class MoviesCacheImpl @Inject constructor(
    private val movieDao: MovieDao,
    private val favoriteMovieDao: FavoriteMovieDao
) : MoviesCache {
    override suspend fun saveMovies(movies: List<MovieCard>) {
        movieDao.saveMovies(movies = movies.map { it.toMovieDB() })
    }

    override suspend fun getTopMovies(): Pair<String, List<MovieCard>> {
        return "Популярные" to movieDao.getTopMovies().map { it.toMovieCard() }
    }

    override suspend fun getFavoriteMovies(): Pair<String, List<MovieCard>> {
        return "Избранное" to favoriteMovieDao.getFavoriteMovies().map { it.toMovieCard() }
    }

    override suspend fun getMovieById(id: Int): MovieCard {
        return movieDao.getMovieById(id).toMovieCard()
    }

    override suspend fun updateMovie(id: Int): List<Pair<String, List<MovieCard>>> {
        val movie = movieDao.getMovieById(id)
        val favoriteMovie = favoriteMovieDao.getMovieById(id)
        if (favoriteMovie == null)
            favoriteMovieDao.setMovieFavorite(movie.toFavoriteMovieDB())
        else
            favoriteMovieDao.removeMovie(favoriteMovie)
        movieDao.setMovieFavorite(movie.copy(isFavorite = !movie.isFavorite))
        return listOf(getTopMovies(), getFavoriteMovies())
    }

    override suspend fun searchTopMoviesByName(name: String): List<MovieCard> {
        return movieDao.searchMovies(name).map { it.toMovieCard() }
    }

    override suspend fun searchFavoriteMoviesByName(name: String): List<MovieCard> {
        return favoriteMovieDao.searchMovies(name).map { it.toMovieCard() }
    }

    companion object {
        fun MovieCard.toMovieDB() =
            MovieDB(id, name, year, genres.joinToString(";"), imageUrl, isFavorite)

        fun FavoriteMovieDB.toMovieCard() = MovieCard(id, name, year, genres.split(';'), posterUrl, isFavorite)

        fun MovieDB.toFavoriteMovieDB() = FavoriteMovieDB(id, name, year, genres, posterUrl, true)

    }

}