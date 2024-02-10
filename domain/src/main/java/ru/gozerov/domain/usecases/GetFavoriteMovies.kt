package ru.gozerov.domain.usecases

import kotlinx.coroutines.flow.Flow
import ru.gozerov.domain.models.MovieCard
import ru.gozerov.domain.repositories.MoviesRepository
import javax.inject.Inject

class GetFavoriteMovies @Inject constructor(
    private val moviesRepository: MoviesRepository
) : UseCase<Unit, Pair<String, List<MovieCard>>>() {

    override suspend fun loadData(arg: Unit): Pair<String, List<MovieCard>> {
        return moviesRepository.getFavoriteMovies()
    }

}