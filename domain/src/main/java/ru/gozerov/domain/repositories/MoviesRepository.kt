package ru.gozerov.domain.repositories

import ru.gozerov.domain.models.Movie
import ru.gozerov.domain.models.MovieCard
import ru.gozerov.domain.models.NameWithCategory

interface MoviesRepository : Repository {

    suspend fun getTopMovies(): Pair<String, List<MovieCard>>

    suspend fun getFavoriteMovies(): Pair<String, List<MovieCard>>

    suspend fun getMovieById(id: Int): Movie

    suspend fun searchMovieByNameAndCategory(data: NameWithCategory): List<MovieCard>

    suspend fun setMovieFavorite(arg: Int) : List<Pair<String, List<MovieCard>>>

}