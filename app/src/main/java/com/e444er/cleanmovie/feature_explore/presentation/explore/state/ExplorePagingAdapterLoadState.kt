package com.e444er.cleanmovie.feature_explore.presentation.explore.state

import com.e444er.cleanmovie.core.presentation.util.UiText
import com.e444er.cleanmovie.feature_home.presentation.home.state.PagingAdapterLoadStateItem


data class ExplorePagingAdapterLoadState(
    val uiText: UiText? = null,
    val filterAdapterState: PagingAdapterLoadStateItem = PagingAdapterLoadStateItem(),
    val searchAdapterState: PagingAdapterLoadStateItem = PagingAdapterLoadStateItem()
)