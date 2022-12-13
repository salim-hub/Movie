package com.ecem.movieapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ecem.movieapp.data.model.Movies

@Database(entities = [Movies::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MoviesDatabase: RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {

        @Volatile
        private var INSTANCE: MoviesDatabase? = null

        fun getInstance(context: Context): MoviesDatabase {
            synchronized(this) {
                var instance: MoviesDatabase? = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MoviesDatabase::class.java,
                        "movies_db"
                    ).allowMainThreadQueries().build()
                }
                return instance
            }
        }
    }
}