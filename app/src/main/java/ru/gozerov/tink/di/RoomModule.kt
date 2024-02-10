package ru.gozerov.tink.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.gozerov.data.movies.cache.room.MovieDatabase

@Module
interface RoomModule {

    @Provides
    fun provideMovieDao(context: Context) = MovieDatabase.getInstance(context).getMovieDao()

}