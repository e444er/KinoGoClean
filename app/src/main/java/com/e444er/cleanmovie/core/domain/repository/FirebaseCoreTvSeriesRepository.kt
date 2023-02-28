package com.e444er.cleanmovie.core.domain.repository

import com.e444er.cleanmovie.core.domain.models.FavoriteTvSeries
import com.e444er.cleanmovie.core.domain.models.TvSeriesWatchListItem
import com.e444er.cleanmovie.core.presentation.util.UiText


interface FirebaseCoreTvSeriesRepository {
    fun addTvSeriesToFavoriteList(
        userUid: String,
        data: Map<String, List<FavoriteTvSeries>>,
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit

    )

    fun addTvSeriesToWatchList(
        userUid: String,
        data: Map<String, List<TvSeriesWatchListItem>>,
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit
    )
}