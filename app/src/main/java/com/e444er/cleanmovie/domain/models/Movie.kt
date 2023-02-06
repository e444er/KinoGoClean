package com.e444er.cleanmovie.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val overview: String,
    val title: String,
    val originalTitle: String,
    val posterPath: String?,
    val backdropPath: String?,
    var releaseDate: String,
    val genreIds: List<Int>,
    val voteCount: Int,
    val voteAverage: Double,
): Parcelable