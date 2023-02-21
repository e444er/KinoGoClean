package com.e444er.cleanmovie.feature_movie_tv_detail.domain.models.credit

data class Cast(
    val id: Int,
    val name: String,
    val originalName: String,
    val profilePath: String?,
    val character: String,
)
