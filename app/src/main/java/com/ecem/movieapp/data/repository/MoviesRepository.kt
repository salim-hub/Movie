package com.ecem.movieapp.data.repository

import android.util.Log
import com.ecem.movieapp.data.model.MoviesResponse
import com.ecem.movieapp.db.MovieDao
import com.ecem.movieapp.service.MovieAppService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepository @Inject constructor(
    private val dao: MovieDao,
    private val appService: MovieAppService
) {

    suspend fun fetchNowPlayingMovies(apikey: String): Response<MoviesResponse> = withContext(Dispatchers.IO) {
        val response = appService.getNowPlayingMovies(apikey)
        if (response.isSuccessful) {
            response.body()?.movies?.let { dao.saveNowPlayingMovies(it) }
        } else {
            Log.d(TAG, "Response error")
        }
        response
    }

    suspend fun fetchUpcomingMovies(apikey: String): Response<MoviesResponse> = withContext(Dispatchers.IO) {
        val response = appService.getUpcomingMovies(apikey)
        if (response.isSuccessful) {
            response.body()?.movies?.let { dao.saveUpcomingMovies(it) }
        } else {
            Log.d(TAG, "Response error")
        }
        response
    }

    companion object {
        private const val TAG = "MoviesRepository"
    }
}