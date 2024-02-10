package ru.gozerov.domain.usecases

import ru.gozerov.domain.models.MovieCard
import ru.gozerov.domain.repositories.MoviesRepository
import javax.inject.Inject

class SearchMovieByName @Inject constructor(
    private val moviesRepository: MoviesRepository
) : UseCase<String, List<MovieCard>>() {

    override suspend fun loadData(arg: String): List<MovieCard> {
        return moviesRepository.searchMovieByName(arg)
    }

}