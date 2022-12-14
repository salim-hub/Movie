package com.ecem.movieapp.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecem.movieapp.data.model.MoviesResponse
import com.ecem.movieapp.data.repository.MoviesRepository
import com.ecem.movieapp.common.Resource
import com.ecem.movieapp.db.MovieDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val dao: MovieDao,
    private val repository: MoviesRepository
) : ViewModel() {

    val movieNowPlaying: MutableLiveData<Resource<MoviesResponse>> = MutableLiveData()
    val upcomingMovies: MutableLiveData<Resource<MoviesResponse>> = MutableLiveData()

    fun fetchNowPlaying(apikey: String){
        movieNowPlaying.postValue(Resource.Loading())
        viewModelScope.launch {
            try {
                val response = repository.fetchNowPlayingMovies(apikey)
                movieNowPlaying.postValue(Resource.Success(response.body()!!))
            } catch (e: Exception) {
                movieNowPlaying.postValue(Resource.Error("Conversion Error ${e.localizedMessage}"))
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            Log.d(TAG, "RESPONSE === ${dao.getNowPlayingMovies()}")
        }
    }

    fun fetchUpcoming(apikey: String) {
        upcomingMovies.postValue(Resource.Loading())
        viewModelScope.launch {
            try {
                val response = repository.fetchUpcomingMovies(apikey)
                upcomingMovies.postValue(Resource.Success(response.body()!!))
            } catch (e: Exception) {
                upcomingMovies.postValue(Resource.Error("Conversion Error ${e.localizedMessage}"))
            }
        }
    }

    companion object {
        private const val TAG = "MoviesViewModel"
    }
}