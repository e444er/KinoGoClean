package com.e444er.cleanmovie.presentation.detail_fragment

import androidx.annotation.StringRes
import com.e444er.cleanmovie.domain.models.MovieDetail
import com.e444er.cleanmovie.domain.models.TvDetail

data class DetailState(
    val loading: Boolean = false,
    val movieDetail: MovieDetail? = null,
    val tvDetail: TvDetail? = null,
    @StringRes val errorId: Int? = null
)
