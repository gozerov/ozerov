package ru.gozerov.domain.usecases

import ru.gozerov.domain.repositories.MoviesRepository
import javax.inject.Inject

class SetMovieFavorite @Inject constructor(
    private val moviesRepository: MoviesRepository
) : UseCase<Int, Unit>() {

    override suspend fun loadData(arg: Int) {
        moviesRepository.setMovieFavorite(arg)
    }

}