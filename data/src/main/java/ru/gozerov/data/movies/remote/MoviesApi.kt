package ru.gozerov.data.movies.remote

import retrofit2.http.GET
import ru.gozerov.data.movies.models.GetTopMoviesResponse

interface MoviesApi {

    @GET("api/v2.2/films/top?type=TOP_100_POPULAR_FILMS")
    suspend fun getTopMovies() : GetTopMoviesResponse


}