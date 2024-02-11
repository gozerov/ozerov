package ru.gozerov.domain.usecases

import kotlinx.coroutines.flow.Flow
import ru.gozerov.domain.models.ErrorMovie
import ru.gozerov.domain.models.MovieCard
import ru.gozerov.domain.models.MovieListData
import ru.gozerov.domain.repositories.MoviesRepository
import java.lang.Exception
import javax.inject.Inject

class GetAllMovies @Inject constructor(
    private val moviesRepository: MoviesRepository
) : UseCase<Unit, List<Pair<String, List<MovieListData>>>>() {

    override suspend fun loadData(arg: Unit): List<Pair<String, List<MovieListData>>> {
        val topMovies: Pair<String, List<MovieListData>> = try {
            val movies = moviesRepository.getTopMovies()
            movies.first to movies.second
        } catch (e: Exception) {
            "Популярные" to listOf(ErrorMovie("Популярные"))
        }
        val favoriteMovies = try {
            val movies = moviesRepository.getFavoriteMovies()
            movies.first to movies.second
        } catch (e: Exception) {
            "Избранное" to listOf(ErrorMovie("Избранное"))
        }
        return listOf(topMovies, favoriteMovies)
    }

}