package ru.gozerov.tink.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.gozerov.data.movies.cache.room.MovieDatabase

@Module
class RoomModule {

    @Provides
    fun provideMovieDao(context: Context) = MovieDatabase.getInstance(context).getMovieDao()

    @Provides
    fun provideFavoriteMovieDao(context: Context) = MovieDatabase.getInstance(context).getFavoriteMovieDao()

}