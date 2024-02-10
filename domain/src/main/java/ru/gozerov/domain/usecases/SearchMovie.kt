package ru.gozerov.domain.usecases

import ru.gozerov.domain.models.MovieCard
import ru.gozerov.domain.models.NameWithCategory
import ru.gozerov.domain.repositories.MoviesRepository
import javax.inject.Inject

class SearchMovie @Inject constructor(
    private val moviesRepository: MoviesRepository
) : UseCase<NameWithCategory, List<MovieCard>>() {

    override suspend fun loadData(arg: NameWithCategory): List<MovieCard> {
        return if (arg.name.isEmpty()) emptyList()
        else
            moviesRepository.searchMovieByNameAndCategory(arg)
    }

}