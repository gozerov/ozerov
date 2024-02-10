package ru.gozerov.domain.usecases

import ru.gozerov.domain.models.MovieCard
import ru.gozerov.domain.repositories.MoviesRepository
import javax.inject.Inject

class SetMovieFavorite @Inject constructor(
    private val moviesRepository: MoviesRepository
) : UseCase<Int, List<Pair<String, List<MovieCard>>>>() {

    override suspend fun loadData(arg: Int): List<Pair<String, List<MovieCard>>> {
        return moviesRepository.setMovieFavorite(arg)
    }

}