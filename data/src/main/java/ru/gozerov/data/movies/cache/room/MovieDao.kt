package ru.gozerov.data.movies.cache.room

import androidx.room.Dao
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
@Dao
interface MovieDao {

    @Query("SELECT * FROM ${MovieConstants.TABLE_NAME}")
    fun getTopMovies(): Flow<MovieDB>

    @Query("SELECT * FROM ${MovieConstants.TABLE_NAME} WHERE is_favorite LIKE 1")
    fun getFavoriteMovies() : Flow<MovieDB>

    @Query("SELECT * FROM ${MovieConstants.TABLE_NAME} WHERE id LIKE :id")
    suspend fun getMovieById(id: Int) : MovieDB

    @Update(MovieDB::class, OnConflictStrategy.REPLACE)
    suspend fun setMovieFavorite(movieDB: MovieDB)

}