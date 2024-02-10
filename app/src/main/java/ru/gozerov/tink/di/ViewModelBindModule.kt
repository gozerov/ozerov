package ru.gozerov.tink.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import ru.gozerov.presentation.screens.movie_list.MovieListViewModel
import kotlin.reflect.KClass

@Module
interface ViewModelBindModule {

    @Binds
    @[IntoMap ViewModelKey(MovieListViewModel::class)]
    fun provideMovieListViewModel(movieListViewModel: MovieListViewModel) : ViewModel

}