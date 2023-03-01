package com.e444er.cleanmovie.feature_movie_tv_detail.domain.models.detail

import com.e444er.cleanmovie.core.data.dto.Genre
import com.e444er.cleanmovie.core.domain.models.TvSeries
import com.e444er.cleanmovie.feature_movie_tv_detail.data.dto.detail.tv.Season
import com.e444er.cleanmovie.feature_movie_tv_detail.data.dto.watch_provider.WatchProviders
import com.e444er.cleanmovie.feature_movie_tv_detail.domain.models.credit.Credit

data class TvDetail(
    val id: Int,
    val genres: List<Genre>,
    val firstAirDate: String,
    val createdBy: List<CreatedBy>,
    val lastAirDate: String,
    val numberOfSeasons: Int,
    val originalName: String,
    val name: String,
    val overview: String,
    val posterPath: String?,
    val seasons: List<Season>,
    val status: String,
    val voteAverage: Double,
    val voteCount: Int,
    var ratingValue: Float = 0f,
    var releaseDate: String = "",
    val credit: Credit,
    val watchProviders: WatchProviders
)


fun TvDetail.toTvSeries(): TvSeries {
    return TvSeries(
        id = id,
        overview = overview,
        name = name,
        originalName = originalName,
        posterPath = posterPath,
        firstAirDate = firstAirDate,
        genreIds = genres.map { it.id },
        voteCount = voteCount,
        voteAverage = voteAverage,
        genreByOne = genres.first().name
    )
}
