package com.e444er.cleanmovie.core.presentation.detail_bottom_sheet

import com.e444er.cleanmovie.feature_home.domain.models.Movie
import com.e444er.cleanmovie.feature_home.domain.models.TvSeries


data class DetailBottomSheetState(
    val movie: Movie? = null,
    val tvSeries: TvSeries? = null
)
