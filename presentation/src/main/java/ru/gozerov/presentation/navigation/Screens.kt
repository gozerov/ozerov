package ru.gozerov.presentation.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.gozerov.presentation.screens.movie_list.MovieListFragment

object Screens {

    fun movieList() = FragmentScreen { MovieListFragment() }

}