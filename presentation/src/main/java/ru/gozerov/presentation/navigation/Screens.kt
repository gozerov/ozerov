package ru.gozerov.presentation.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.gozerov.presentation.screens.movie_details.MovieDetailsFragment
import ru.gozerov.presentation.screens.movie_list.MovieListFragment
import ru.gozerov.presentation.screens.movie_list.TabType
import ru.gozerov.presentation.screens.search.SearchMovieFragment

object Screens {

    fun movieList() = FragmentScreen { MovieListFragment() }

    fun movieDetails(id: Int) = FragmentScreen { MovieDetailsFragment.newInstance(id) }

    fun searchMovie(tabType: TabType) = FragmentScreen { SearchMovieFragment.newInstance(tabType) }

}