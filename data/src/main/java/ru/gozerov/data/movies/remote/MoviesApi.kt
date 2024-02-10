package ru.gozerov.data.movies.remote

import retrofit2.http.GET
import retrofit2.http.Path
import ru.gozerov.data.movies.models.GetTopMoviesResponse
import ru.gozerov.data.movies.models.MovieData

interface MoviesApi {

    @GET("api/v2.2/films/top?type=TOP_100_POPULAR_FILMS")
    suspend fun getTopMovies() : GetTopMoviesResponse

    @GET("api/v2.2/films/{id}")
    suspend fun getMovieById(@Path("id") id: Int) : MovieData

}