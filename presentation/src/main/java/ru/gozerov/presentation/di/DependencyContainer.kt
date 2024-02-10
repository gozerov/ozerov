package ru.gozerov.presentation.di

import android.content.Context
import androidx.fragment.app.Fragment
import ru.gozerov.presentation.activity.MainActivity
import ru.gozerov.presentation.screens.movie_details.MovieDetailsFragment

interface DependencyContainer {

    fun inject(activity: MainActivity)
    fun inject(movieDetailsFragment: MovieDetailsFragment)

}

val Context.appComponent : DependencyContainer
    get() = (this.applicationContext as DependencyProvider).get()

val Fragment.appComponent : DependencyContainer
    get() = this.requireContext().appComponent
