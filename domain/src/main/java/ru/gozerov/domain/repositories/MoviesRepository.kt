package ru.gozerov.domain.repositories

import kotlinx.coroutines.flow.Flow
import ru.gozerov.domain.models.Movie
import ru.gozerov.domain.models.MovieCard

interface MoviesRepository : Repository {

    suspend fun getTopMovies(): Pair<String, List<MovieCard>>

    suspend fun getFavoriteMovies(): Pair<String, List<MovieCard>>

    suspend fun getMovieById(id: Int): Movie

    suspend fun searchMovieByName(name: String): List<MovieCard>

    suspend fun setMovieFavorite(arg: Int) : List<Pair<String, List<MovieCard>>>

}