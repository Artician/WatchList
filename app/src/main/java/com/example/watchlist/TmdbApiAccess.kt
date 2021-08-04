package com.example.watchlist

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private var BASE_URL = "https://api.themoviedb.org/3"
private var API_KEY = "1f645a3aee0745d3ea058062fc71657d"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface TmdbApiAccess {
    @GET("configuration?api_key=1f645a3aee0745d3ea058062fc71657d")
    suspend fun getConfig(): ConfigReply

    @GET("movie/popular?api_key=1f645a3aee0745d3ea058062fc71657d&language=en-US")
    suspend fun getMovies(): MovieListReply
}

object TmdbApi{
    val retrofitService : TmdbApiAccess by lazy {
        retrofit.create(TmdbApiAccess::class.java)
    }
}