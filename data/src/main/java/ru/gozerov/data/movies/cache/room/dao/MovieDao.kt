package ru.gozerov.data.movies.cache.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.gozerov.data.movies.cache.room.MovieConstants
import ru.gozerov.data.movies.cache.room.entity.MovieDB

@Dao
interface MovieDao {

    @Query("SELECT * FROM ${MovieConstants.MOVIES_TABLE_NAME}")
    fun getTopMovies(): List<MovieDB>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovies(movies: List<MovieDB>)

    @Query("SELECT * FROM ${MovieConstants.MOVIES_TABLE_NAME} WHERE id LIKE :id")
    suspend fun getMovieById(id: Int) : MovieDB

    @Update(MovieDB::class, OnConflictStrategy.REPLACE)
    suspend fun setMovieFavorite(movieDB: MovieDB)

    @Query("SELECT * FROM ${MovieConstants.MOVIES_TABLE_NAME} WHERE name LIKE '%' || :name || '%'")
    fun searchMovies(name: String) : List<MovieDB>

}