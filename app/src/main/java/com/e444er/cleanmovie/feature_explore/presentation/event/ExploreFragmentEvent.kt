package com.e444er.cleanmovie.feature_explore.presentation.event

import com.e444er.cleanmovie.core.domain.repository.ConnectivityObserver
import com.e444er.cleanmovie.feature_explore.presentation.explore.ExploreFragmentDirections

sealed class ExploreFragmentEvent {
    data class MultiSearch(val query: String) : ExploreFragmentEvent()
    object RemoveQuery : ExploreFragmentEvent()
    data class NavigateToDetailBottomSheet(val directions: ExploreFragmentDirections.ActionExploreFragmentToDetailBottomSheet) :
        ExploreFragmentEvent()

    data class UpdateConnectivityStatus(val connectivityStatus: ConnectivityObserver.Status) :
        ExploreFragmentEvent()

    data class NavigateToPersonDetail(
        val directions: ExploreFragmentDirections.ActionExploreFragmentToPersonDetailFragment
        ) :
        ExploreFragmentEvent()
}