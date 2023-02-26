package com.e444er.cleanmovie.feature_explore.presentation.event

sealed class ExploreFragmentEvent {
    data class MultiSearch(val query: String) : ExploreFragmentEvent()
    object RemoveQuery : ExploreFragmentEvent()
}
