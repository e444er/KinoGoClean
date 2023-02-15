package com.e444er.cleanmovie.domain.models

import com.e444er.cleanmovie.domain.models.credit.Credit
import com.prmto.mova_movieapp.data.models.watch_provider.WatchProviders

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
