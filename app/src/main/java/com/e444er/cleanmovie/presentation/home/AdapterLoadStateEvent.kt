package com.e444er.cleanmovie.presentation.home

import com.e444er.cleanmovie.presentation.util.UiText


sealed class AdapterLoadStateEvent {

    data class PagingError(val uiText: UiText) : AdapterLoadStateEvent()

    object NowPlayingLoading : AdapterLoadStateEvent()
    object NowPlayingNotLoading : AdapterLoadStateEvent()

    object PopularMoviesLoading : AdapterLoadStateEvent()
    object PopularMoviesNotLoading : AdapterLoadStateEvent()

    object PopularTvSeriesLoading : AdapterLoadStateEvent()
    object PopularTvSeriesNotLoading : AdapterLoadStateEvent()

    object TopRatedMoviesLoading : AdapterLoadStateEvent()
    object TopRatedMoviesNotLoading : AdapterLoadStateEvent()


    object TopRatedTvSeriesLoading : AdapterLoadStateEvent()
    object TopRatedTvSeriesNotLoading : AdapterLoadStateEvent()
}