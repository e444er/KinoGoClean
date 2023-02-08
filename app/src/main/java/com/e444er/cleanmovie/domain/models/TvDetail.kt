package com.e444er.cleanmovie.domain.models

import com.e444er.cleanmovie.data.models.detail.tv.Season
import com.squareup.moshi.Json

data class TvDetail(
    val id: Int,
    val genres: List<Genre>,
    @Json(name = "first_air_date") val firstAirDate: String,
    @Json(name = "last_air_date") val lastAirDate: String,
    @Json(name = "number_of_seasons") val numberOfSeasons: Int,
    @Json(name = "original_name") val originalName: String,
    val name: String,
    val overview: String,
    @Json(name = "poster_path") val posterPath: String?,
    val seasons: List<Season>,
    val status: String,
    @Json(name = "vote_average") val voteAverage: Double,
    @Json(name = "vote_count") val voteCount: Int
)
