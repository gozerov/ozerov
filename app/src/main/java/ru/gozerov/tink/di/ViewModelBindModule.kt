package ru.gozerov.tink.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import ru.gozerov.presentation.screens.movie_details.MovieDetailsViewModel
import ru.gozerov.presentation.screens.movie_list.MovieListViewModel
import ru.gozerov.presentation.screens.search.SearchMovieViewModel
import kotlin.reflect.KClass

@Module
interface ViewModelBindModule {

    @Binds
    @[IntoMap ViewModelKey(MovieListViewModel::class)]
    fun provideMovieListViewModel(movieListViewModel: MovieListViewModel) : ViewModel

    @Binds
    @[IntoMap ViewModelKey(MovieDetailsViewModel::class)]
    fun provideMovieDetailsViewModel(movieDetailsViewModel: MovieDetailsViewModel) : ViewModel


    @Binds
    @[IntoMap ViewModelKey(SearchMovieViewModel::class)]
    fun provideSearchMovieViewModel(searchMovieViewModel: SearchMovieViewModel) : ViewModel

}