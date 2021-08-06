package com.example.watchlist

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

// Class to wrap the API requests and expose usable data to the rest of the app
private var BASE_URL = "https://api.themoviedb.org/3/"
// Frankly, it was simpler than intercepting all HTTP calls, and let me get a working app
private var API_KEY = "1f645a3aee0745d3ea058062fc71657d"

// Moshi object and builder
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

// Retrofit object and builder
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

// Wrapper for HTTP requests
interface TmdbApiAccess {
    @GET("configuration?api_key=1f645a3aee0745d3ea058062fc71657d")
    suspend fun getConfig(): ConfigReply

    @GET("movie/popular?api_key=1f645a3aee0745d3ea058062fc71657d&language=en-US")
    suspend fun getMovies(): MovieListReply
}

// Singleton object for outside access
object TmdbApi{
    val retrofitService : TmdbApiAccess by lazy {
        retrofit.create(TmdbApiAccess::class.java)
    }
}