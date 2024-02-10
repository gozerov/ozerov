package ru.gozerov.presentation.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.gozerov.presentation.screens.movie_list.MovieListFragment
import ru.gozerov.presentation.screens.search.SearchMovieFragment

object Screens {

    fun movieList() = FragmentScreen { MovieListFragment() }

    fun searchMovie() = FragmentScreen { SearchMovieFragment() }

}