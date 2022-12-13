package com.ecem.movieapp.data.repository

import com.ecem.movieapp.data.model.MoviesResponse
import com.ecem.movieapp.service.MovieAppService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepository @Inject constructor(
    private val appService: MovieAppService
) {

    suspend fun fetchNowPlayingMovies(apikey: String): Response<MoviesResponse> = withContext(
        Dispatchers.IO) {
        val nowPlaying = appService.getNowPlayingMovies(apikey)
        nowPlaying
    }

    suspend fun fetchUpcomingMovies(apikey: String): Response<MoviesResponse> = withContext(
        Dispatchers.IO) {
        val upcoming = appService.getUpcomingMovies(apikey)
        upcoming
    }
}