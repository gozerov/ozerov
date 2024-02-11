package ru.gozerov.domain.usecases

import ru.gozerov.domain.models.MovieListData
import ru.gozerov.domain.repositories.MoviesRepository
import javax.inject.Inject

class GetAllMovies @Inject constructor(
    private val moviesRepository: MoviesRepository
) : UseCase<Unit, List<MovieListData>>() {

    override suspend fun loadData(arg: Unit): List<MovieListData> {
        val topMovies: MovieListData = try {
            val movies = moviesRepository.getTopMovies()
            MovieListData.MovieList(movies.first, movies.second)
        } catch (e: Exception) {
            MovieListData.ErrorMovie("Популярные")
        }
        val favoriteMovies: MovieListData = try {
            val movies = moviesRepository.getFavoriteMovies()
            MovieListData.MovieList(movies.first, movies.second)
        } catch (e: Exception) {
            MovieListData.ErrorMovie("Избранное")
        }
        return listOf(topMovies, favoriteMovies)
    }

}