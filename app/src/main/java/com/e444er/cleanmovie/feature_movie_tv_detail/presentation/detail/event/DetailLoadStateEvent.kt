package com.e444er.cleanmovie.feature_movie_tv_detail.presentation.detail.event

import com.e444er.cleanmovie.core.presentation.util.UiText


sealed class DetailLoadStateEvent {
    object RecommendationLoading : DetailLoadStateEvent()
    object RecommendationNotLoading : DetailLoadStateEvent()
    data class PagingError(val uiText: UiText) : DetailLoadStateEvent()
}
