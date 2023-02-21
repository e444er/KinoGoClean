package com.e444er.cleanmovie.feature_movie_tv_detail.presentation.detail

import com.e444er.cleanmovie.core.util.Constants.DETAIL_DEFAULT_ID
import com.e444er.cleanmovie.feature_movie_tv_detail.domain.models.detail.MovieDetail
import com.e444er.cleanmovie.feature_movie_tv_detail.domain.models.detail.TvDetail

data class DetailState(
    val loading: Boolean = false,
    val movieDetail: MovieDetail? = null,
    val tvDetail: TvDetail? = null,
    val movieId: Int = DETAIL_DEFAULT_ID,
    val tvId: Int = DETAIL_DEFAULT_ID
)
