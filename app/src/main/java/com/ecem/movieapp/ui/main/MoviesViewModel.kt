package com.ecem.movieapp.ui.main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecem.movieapp.data.model.MoviesResponse
import com.ecem.movieapp.data.repository.MoviesRepository
import com.ecem.movieapp.common.Resource
import com.ecem.movieapp.common.hasInternetConnection
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val repository: MoviesRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    val movieNowPlaying: MutableLiveData<Resource<MoviesResponse>> = MutableLiveData()
    val upcomingMovies: MutableLiveData<Resource<MoviesResponse>> = MutableLiveData()

    fun fetchNowPlaying(apikey: String){
        movieNowPlaying.postValue(Resource.Loading())
        viewModelScope.launch {
            try {
                if (hasInternetConnection(context)) {
                    val response = repository.fetchNowPlayingMovies(apikey)
                    movieNowPlaying.postValue(Resource.Success(response.body()!!))
                } else
                    movieNowPlaying.postValue(Resource.Error("Internet Connection Error"))
            } catch (ex: Exception) {
                when (ex) {
                    is IOException -> movieNowPlaying.postValue(Resource.Error("Network Failure " +  ex.localizedMessage))
                    else -> movieNowPlaying.postValue(Resource.Error("Conversion Error"))
                }
            }
        }
    }

    fun fetchUpcoming(apikey: String) {
        upcomingMovies.postValue(Resource.Loading())
        viewModelScope.launch {
            try {
                if (hasInternetConnection(context)) {
                    val response = repository.fetchUpcomingMovies(apikey)
                    upcomingMovies.postValue(Resource.Success(response.body()!!))
                } else
                    upcomingMovies.postValue(Resource.Error("Internet Connection Error"))
            } catch (ex: Exception) {
                when (ex) {
                    is IOException -> upcomingMovies.postValue(Resource.Error("Network Failure " +  ex.localizedMessage))
                    else -> upcomingMovies.postValue(Resource.Error("Conversion Error"))
                }
            }
        }
    }
}