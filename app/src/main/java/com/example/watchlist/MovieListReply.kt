package com.example.watchlist

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

// This was the easy part. I had no issue parsing the JSON objects with Retrofit and Moshi
@JsonClass(generateAdapter = true)
data class MovieListReply(
    val page: Int,
    val results: List<Movie>
)

@JsonClass(generateAdapter = true)
data class Movie(
    @Json(name = "poster_path") val posterPath: String?,
    val overview : String,
    @Json(name = "release_date") val releaseDate: String,
    @Json(name = "genre_ids") val genreIds: List<Int>,
    val title: String,
    @Json(name = "vote_count") val voteCount: Double
)