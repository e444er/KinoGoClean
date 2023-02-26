package com.e444er.cleanmovie.feature_home.presentation.home.state

import com.e444er.cleanmovie.core.presentation.util.UiText

data class HomePagingAdapterLoadState(
    val error: UiText? = null,
    val nowPlayingState: PagingAdapterLoadStateItem = PagingAdapterLoadStateItem(),
    val popularMoviesState: PagingAdapterLoadStateItem = PagingAdapterLoadStateItem(),
    val popularTvSeriesState: PagingAdapterLoadStateItem = PagingAdapterLoadStateItem(),
    val topRatedMoviesState: PagingAdapterLoadStateItem = PagingAdapterLoadStateItem(),
    val topRatedTvSeriesState: PagingAdapterLoadStateItem = PagingAdapterLoadStateItem(),
)

data class PagingAdapterLoadStateItem(
    val isLoading: Boolean = false,
)