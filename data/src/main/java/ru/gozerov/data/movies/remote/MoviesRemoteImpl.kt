package ru.gozerov.data.movies.remote

import ru.gozerov.data.movies.models.MovieCardData
import ru.gozerov.data.movies.models.MovieData
import javax.inject.Inject

class MoviesRemoteImpl @Inject constructor(
    private val moviesApi: MoviesApi
) : MoviesRemote {

    override suspend fun getTopMovies(): List<MovieCardData> {
        return moviesApi.getTopMovies().films
    }

    override suspend fun getMovieById(id: Int): MovieData {
        throw Exception()
    }

}