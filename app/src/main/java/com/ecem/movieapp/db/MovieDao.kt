package com.ecem.movieapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ecem.movieapp.data.model.Movies

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNowPlayingMovies(movies: List<Movies>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUpcomingMovies(movies: List<Movies>)

    @Query("SELECT * FROM movies")
    fun getNowPlayingMovies(): List<Movies>

    @Query("SELECT * FROM movies")
    fun getUpcomingMovies(): List<Movies>

}