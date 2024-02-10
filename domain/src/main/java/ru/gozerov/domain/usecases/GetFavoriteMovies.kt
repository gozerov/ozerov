package ru.gozerov.domain.usecases

import ru.gozerov.domain.models.MovieCard
import ru.gozerov.domain.repositories.MoviesRepository
import javax.inject.Inject

class GetFavoriteMovies @Inject constructor(
    private val moviesRepository: MoviesRepository
) : UseCase<Unit, List<MovieCard>>() {

    override suspend fun loadData(arg: Unit): List<MovieCard> {
        return moviesRepository.getFavoriteMovies()
    }

}