package ru.gozerov.data.movies.cache.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface MovieDao {

    @Query("SELECT * FROM ${MovieConstants.TABLE_NAME}")
    fun getTopMovies(): List<MovieDB>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovies(movies: List<MovieDB>)

    @Query("SELECT * FROM ${MovieConstants.TABLE_NAME} WHERE is_favorite LIKE 1")
    fun getFavoriteMovies() : List<MovieDB>

    @Query("SELECT * FROM ${MovieConstants.TABLE_NAME} WHERE id LIKE :id")
    suspend fun getMovieById(id: Int) : MovieDB

    @Update(MovieDB::class, OnConflictStrategy.REPLACE)
    suspend fun setMovieFavorite(movieDB: MovieDB)

    @Query("SELECT * FROM ${MovieConstants.TABLE_NAME} WHERE name LIKE '%' || :name || '%'")
    fun searchMovies(name: String) : List<MovieDB>

}