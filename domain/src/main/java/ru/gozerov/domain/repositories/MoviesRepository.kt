package ru.gozerov.domain.repositories

import ru.gozerov.domain.models.Movie
import ru.gozerov.domain.models.MovieCard

interface MoviesRepository : Repository {

    suspend fun getTopMovies() : List<MovieCard>

    suspend fun getFavoriteMovies() : List<MovieCard>

    suspend fun getMovieById(id: Int) : Movie

    suspend fun searchMovieByName(name: String) : List<MovieCard>

}