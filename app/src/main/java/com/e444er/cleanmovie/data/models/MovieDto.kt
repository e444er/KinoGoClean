package com.e444er.cleanmovie.data.models

import com.e444er.cleanmovie.domain.models.Movie
import com.squareup.moshi.Json

data class MovieDto(
    val id: Int,
    val adult: Boolean,
    val overview: String,
    val title: String,
    val popularity: Double,
    val video: Boolean,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "release_date") val releaseDate: String,
    @Json(name = "genre_ids") val genreIds: List<Int>,
    @Json(name = "original_title") val originalTitle: String,
    @Json(name = "original_language") val originalLanguage: String,
    @Json(name = "backdrop_path") val backdropPath: String?,
    @Json(name = "vote_count") val voteCount: Int,
    @Json(name = "vote_average") val voteAverage: Double
)

fun MovieDto.toMovie(): Movie {
    return Movie(
        id = id,
        overview = overview,
        title = title,
        originalTitle = originalTitle,
        posterPath = posterPath,
        backdropPath = backdropPath,
        releaseDate = releaseDate,
        genreIds = genreIds,
        voteCount = voteCount,
        voteAverage = voteAverage
    )
}