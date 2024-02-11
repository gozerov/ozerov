package ru.gozerov.data.movies.cache.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteMovieDao {

    @Query("SELECT * FROM ${MovieConstants.FAVORITE_MOVIES_TABLE_NAME}")
    suspend fun getFavoriteMovies() : List<FavoriteMovieDB>

    @Query("SELECT * FROM ${MovieConstants.FAVORITE_MOVIES_TABLE_NAME} WHERE id LIKE :id")
    suspend fun getMovieById(id: Int) : FavoriteMovieDB?

    @Delete
    suspend fun removeMovie(favoriteMovieDB: FavoriteMovieDB)

    @Insert
    suspend fun setMovieFavorite(favoriteMovieDB: FavoriteMovieDB)

    @Query("SELECT * FROM ${MovieConstants.FAVORITE_MOVIES_TABLE_NAME} WHERE name LIKE '%' || :name || '%'")
    fun searchMovies(name: String) : List<MovieDB>

}