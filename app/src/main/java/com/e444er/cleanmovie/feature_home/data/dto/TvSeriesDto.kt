package com.e444er.cleanmovie.feature_home.data.dto

import com.e444er.cleanmovie.feature_home.domain.models.TvSeries
import com.squareup.moshi.Json

data class TvSeriesDto(
    val id: Int,
    val popularity: Double,
    val overview: String,
    val name: String,
    @Json(name = "original_name") val originalName: String,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "first_air_date") val firstAirDate: String?,
    @Json(name = "origin_country") val originCountry: List<String>,
    @Json(name = "genre_ids") val genreIds: List<Int>,
    @Json(name = "original_language") val originalLanguage: String,
    @Json(name = "vote_average") val voteAverage: Double,
    @Json(name = "vote_count") val voteCount: Int,
)

fun List<TvSeriesDto>.toTvSeries(): List<TvSeries> {
    return map {
        TvSeries(
            id = it.id,
            overview = it.overview,
            name = it.name,
            originalName = it.originalName,
            posterPath = it.posterPath,
            firstAirDate = it.firstAirDate,
            genreIds = it.genreIds,
            voteCount = it.voteCount,
            voteAverage = it.voteAverage
        )
    }
}