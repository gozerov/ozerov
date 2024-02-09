package ru.gozerov.data.movies.remote

import ru.gozerov.data.movies.models.MovieCardData
import ru.gozerov.data.movies.models.MovieData

interface MoviesRemote {

    suspend fun getTopMovies() : List<MovieCardData>

    suspend fun getMovieById(id: Int) : MovieData

}