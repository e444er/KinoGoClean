package com.e444er.cleanmovie.presentation.detail_fragment

import com.e444er.cleanmovie.domain.models.MovieDetail
import com.e444er.cleanmovie.domain.models.TvDetail
import com.e444er.cleanmovie.presentation.util.UiText
import com.e444er.cleanmovie.util.Constants.DETAIL_DEFAULT_ID

data class DetailState(
    val loading: Boolean = false,
    val movieDetail: MovieDetail? = null,
    val tvDetail: TvDetail? = null,
    val movieId: Int = DETAIL_DEFAULT_ID,
    val tvId: Int = DETAIL_DEFAULT_ID
)
