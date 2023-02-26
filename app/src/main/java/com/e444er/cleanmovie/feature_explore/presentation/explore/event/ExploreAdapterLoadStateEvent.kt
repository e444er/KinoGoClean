package com.e444er.cleanmovie.feature_explore.presentation.explore.event

import com.e444er.cleanmovie.core.presentation.util.UiText


sealed class ExploreAdapterLoadStateEvent {
    data class PagingError(val uiText: UiText) : ExploreAdapterLoadStateEvent()

    object FilterAdapterLoading : ExploreAdapterLoadStateEvent()
    object FilterAdapterNotLoading : ExploreAdapterLoadStateEvent()

    object SearchAdapterLoading : ExploreAdapterLoadStateEvent()
    object SearchAdapterNotLoading : ExploreAdapterLoadStateEvent()
}
