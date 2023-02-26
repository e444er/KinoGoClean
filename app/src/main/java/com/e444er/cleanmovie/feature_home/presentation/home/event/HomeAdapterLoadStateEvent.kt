package com.e444er.cleanmovie.feature_home.presentation.home.event

import com.e444er.cleanmovie.core.presentation.util.UiText


sealed class HomeAdapterLoadStateEvent {

    data class PagingError(val uiText: UiText) : HomeAdapterLoadStateEvent()

    object NowPlayingLoading : HomeAdapterLoadStateEvent()
    object NowPlayingNotLoading : HomeAdapterLoadStateEvent()

    object PopularMoviesLoading : HomeAdapterLoadStateEvent()
    object PopularMoviesNotLoading : HomeAdapterLoadStateEvent()

    object PopularTvSeriesLoading : HomeAdapterLoadStateEvent()
    object PopularTvSeriesNotLoading : HomeAdapterLoadStateEvent()

    object TopRatedMoviesLoading : HomeAdapterLoadStateEvent()
    object TopRatedMoviesNotLoading : HomeAdapterLoadStateEvent()


    object TopRatedTvSeriesLoading : HomeAdapterLoadStateEvent()
    object TopRatedTvSeriesNotLoading : HomeAdapterLoadStateEvent()
}
