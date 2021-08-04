package com.example.watchlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.lang.Exception

class MoviesViewModel : ViewModel() {

    private val status = MutableLiveData<String>()
    private val rawMovies = MutableLiveData<MovieListReply>()
    private val rawConfigInfo = MutableLiveData<ConfigReply>()

    val movies: LiveData<MovieListReply> = rawMovies
    val configInfo: LiveData<ConfigReply> = rawConfigInfo

    init{
        getMovies()
    }


    private fun getMovies(){
        viewModelScope.launch {
            try {
                rawMovies.value = TmdbApi.retrofitService.getMovies()
                rawConfigInfo.value = TmdbApi.retrofitService.getConfig()
                status.value = "Data from source gathered"
            } catch (e: Exception){
                status.value = "Failure: ${e.message}"
            }
        }
    }
}