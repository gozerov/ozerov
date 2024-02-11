package ru.gozerov.presentation.screens.search

import ru.gozerov.presentation.screens.movie_list.TabType

sealed class SearchMovieIntent {

    data class SearchByName(
        val name: String,
        val tabType: TabType
    ) : SearchMovieIntent()

}
