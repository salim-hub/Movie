package com.ecem.movieapp.di

import android.app.Application
import androidx.room.Room
import com.ecem.movieapp.db.MoviesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun injectDB(application: Application) = Room.databaseBuilder(
        application, MoviesDatabase::class.java, "movies_db"
    ).build()

    @Singleton
    @Provides
    fun injectDao(database: MoviesDatabase) = database.movieDao()
}