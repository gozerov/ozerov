package ru.gozerov.domain.usecases

import ru.gozerov.domain.models.MovieListData
import ru.gozerov.domain.repositories.MoviesRepository
import javax.inject.Inject

class SetMovieFavorite @Inject constructor(
    private val moviesRepository: MoviesRepository
) : UseCase<Int, List<MovieListData>>() {

    override suspend fun loadData(arg: Int): List<MovieListData> {
        return moviesRepository.setMovieFavorite(arg)
            .map { MovieListData.MovieList(it.first, it.second) }
    }

}