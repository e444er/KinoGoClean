package com.e444er.cleanmovie.feature_authentication.domain.repository

import com.e444er.cleanmovie.core.domain.models.FavoriteTvSeries
import com.e444er.cleanmovie.core.domain.models.TvSeriesWatchListItem
import com.e444er.cleanmovie.core.presentation.util.UiText


interface FirebaseTvSeriesRepository {

    fun getFavoriteTvSeries(
        userUid: String,
        onSuccess: (List<FavoriteTvSeries>) -> Unit,
        onFailure: (uiText: UiText) -> Unit
    )

    fun getTvSeriesInWatchList(
        userUid: String,
        onSuccess: (List<TvSeriesWatchListItem>) -> Unit,
        onFailure: (uiText: UiText) -> Unit
    )
}