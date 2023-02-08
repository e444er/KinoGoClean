package com.e444er.cleanmovie.domain.models

import com.squareup.moshi.Json

data class MovieDetail(
    val id: Int,
    val genres: List<Genre>,
    @Json(name = "imdb_id") val imdbId: String?,
    @Json(name = "original_title") val originalTitle: String,
    val overview: String?,
    val popularity: Double,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "release_date") val releaseDate: String,
    val runtime: Int?,
    @Json(name = "vote_average") val voteAverage: Double,
    @Json(name = "vote_count") val voteCount: Int
)
