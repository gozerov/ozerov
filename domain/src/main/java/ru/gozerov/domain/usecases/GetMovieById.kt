package ru.gozerov.domain.usecases

import ru.gozerov.domain.models.Movie
import ru.gozerov.domain.repositories.MoviesRepository
import javax.inject.Inject

class GetMovieById @Inject constructor(
    private val moviesRepository: MoviesRepository
) : UseCase<Int, Movie>() {

    override suspend fun loadData(arg: Int): Movie {
        return moviesRepository.getMovieById(arg)
    }

}