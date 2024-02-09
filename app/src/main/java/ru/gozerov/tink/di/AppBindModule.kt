package ru.gozerov.tink.di

import dagger.Binds
import dagger.Module
import ru.gozerov.data.movies.remote.MoviesRemote
import ru.gozerov.data.movies.remote.MoviesRemoteImpl
import ru.gozerov.data.movies.repository.MoviesRepositoryImpl
import ru.gozerov.domain.repositories.MoviesRepository
import javax.inject.Singleton

@Module
interface AppBindModule {

    @Binds
    @Singleton
    fun bindMoviesRemoteImplToMoviesRemote(moviesRemoteImpl: MoviesRemoteImpl): MoviesRemote

    @Binds
    @Singleton
    fun bindMoviesRepoImplToGoodsRepo(moviesRepositoryImpl: MoviesRepositoryImpl): MoviesRepository

}