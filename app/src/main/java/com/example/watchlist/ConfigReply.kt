package com.example.watchlist

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

// This is mostly unused. It pulls in the data needed, but synchronizing the inputs was
// too much time.
@JsonClass(generateAdapter = true)
data class ConfigReply (
    val images: ImageConfig
    )


@JsonClass(generateAdapter = true)
data class ImageConfig(
    @Json(name = "base_url") val imgBaseUrl: String,
    @Json(name = "logo_sizes") val logoSizes: List<String>,
    @Json(name = "poster_sizes") val posterSizes: List<String>
)