package com.e444er.cleanmovie.feature_movie_tv_detail.domain.models.detail

import com.e444er.cleanmovie.core.data.dto.Genre
import com.e444er.cleanmovie.core.domain.models.Movie
import com.e444er.cleanmovie.feature_movie_tv_detail.data.dto.watch_provider.WatchProviders
import com.e444er.cleanmovie.feature_movie_tv_detail.domain.models.credit.Credit


data class MovieDetail(
    val id: Int,
    val genres: List<Genre>,
    val imdbId: String?,
    val originalTitle: String,
    val title: String,
    val overview: String?,
    val posterPath: String?,
    val releaseDate: String,
    val runtime: Int?,
    val voteAverage: Double,
    val voteCount: Int,
    var convertedRuntime: Map<String, String> = emptyMap(),
    val credit: Credit,
    var ratingValue: Float = 0f,
    val watchProviders: WatchProviders
)


fun MovieDetail.toMovie(): Movie {
    return Movie(
        id = id,
        overview = overview ?: "",
        title = title,
        originalTitle = originalTitle,
        posterPath = posterPath,
        releaseDate = releaseDate,
        genreIds = genres.map { it.id },
        voteCount = voteCount,
        voteAverage = voteAverage,
        genreByOne = genres.first().name
    )
}
