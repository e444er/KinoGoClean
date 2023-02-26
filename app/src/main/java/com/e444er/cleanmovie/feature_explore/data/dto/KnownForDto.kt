package com.e444er.cleanmovie.feature_explore.data.dto

import com.e444er.cleanmovie.feature_explore.domain.model.KnownForSearch
import com.squareup.moshi.Json

data class KnownForDto(
    val adult: Boolean?,
    @Json(name = "backdrop_path") val backdropPath: String?,
    @Json(name = "first_air_date") val firstAirDate: String?,
    @Json(name = "genre_ids") val genreIds: List<Int>?,
    val id: Int,
    @Json(name = "media_type") val mediaType: String,
    val name: String?,
    @Json(name = "origin_country") val originCountry: List<String>?,
    @Json(name = "original_language") val originalLanguage: String?,
    @Json(name = "original_name") val originalName: String?,
    @Json(name = "original_title") val originalTitle: String?,
    val overview: String?,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "release_date") val releaseDate: String?,
    val title: String?,
    val video: Boolean?,
    @Json(name = "vote_average") val voteAverage: Double,
    @Json(name = "vote_count") val voteCount: Int
)


fun List<KnownForDto?>.toKnownForSearch(): List<KnownForSearch> {
    return map {
        KnownForSearch(
            id = it!!.id,
            overview = it.overview,
            firstAirDate = it.firstAirDate,
            genreIds = it.genreIds,
            mediaType = it.mediaType,
            name = it.name,
            originalName = it.originalName,
            originalTitle = it.originalTitle,
            posterPath = it.posterPath,
            releaseDate = it.releaseDate,
            title = it.title,
            voteAverage = it.voteAverage,
            voteCount = it.voteCount
        )
    }
}