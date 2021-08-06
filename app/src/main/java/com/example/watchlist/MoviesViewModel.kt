package com.example.watchlist

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MoviesViewModel : ViewModel() {
    // Livedata handlers
    private val rawStatus = MutableLiveData<String>()
    private val rawMovies = MutableLiveData<MovieListReply>()
    private val rawConfigInfo = MutableLiveData<ConfigReply>()

    init{
        getMovies()
    }

    fun getMovieList(): MutableLiveData<MovieListReply>{
        return rawMovies
    }

    fun getConfig(): MutableLiveData<ConfigReply>{
        return rawConfigInfo
    }

    private fun getMovies(){
        // Asynchronous tasks
        viewModelScope.launch {
            try {
                // Logging data to ensure objects are populated
                rawMovies.value = TmdbApi.retrofitService.getMovies()
                Log.i("getMovies", "${rawMovies.value!!.results[0].title} retrieved")
                rawConfigInfo.value = TmdbApi.retrofitService.getConfig()
                Log.i("getConfig", "${rawConfigInfo.value!!.images.imgBaseUrl} as Base IMG URL retrieved")
                rawStatus.value = "Data from source gathered"
            } catch (e: Exception){
                rawStatus.value = "Failure: ${e.message}"
            }
        }
    }
}