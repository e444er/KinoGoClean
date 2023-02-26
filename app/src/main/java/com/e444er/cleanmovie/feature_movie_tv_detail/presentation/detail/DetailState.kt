package com.e444er.cleanmovie.feature_movie_tv_detail.presentation.detail

import com.e444er.cleanmovie.feature_movie_tv_detail.domain.models.detail.MovieDetail
import com.e444er.cleanmovie.feature_movie_tv_detail.domain.models.detail.TvDetail

data class DetailState(
    val loading: Boolean = false,
    val movieDetail: MovieDetail? = null,
    val tvDetail: TvDetail? = null
)
