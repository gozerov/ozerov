package ru.gozerov.data.movies.cache.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MovieDB::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun getMovieDao(): MovieDao

    companion object {
        private var database: MovieDatabase? = null

        @Synchronized
        fun getInstance(context: Context): MovieDatabase {
            return database ?: Room.databaseBuilder(
                context,
                MovieDatabase::class.java,
                MovieConstants.TABLE_NAME
            ).build()
        }
    }

}