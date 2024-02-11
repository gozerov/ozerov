package ru.gozerov.data.movies.cache.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.gozerov.data.movies.cache.room.MovieConstants

@Entity(tableName = MovieConstants.FAVORITE_MOVIES_TABLE_NAME)
data class FavoriteMovieDB(
    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "year")
    val year: String,

    @ColumnInfo(name = "genres")
    val genres: String,

    @ColumnInfo(name = "poster_url")
    val posterUrl: String,

    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean
)
