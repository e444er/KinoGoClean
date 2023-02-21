package com.e444er.cleanmovie.presentation.home

import androidx.paging.CombinedLoadStates
import com.e444er.cleanmovie.R
import com.e444er.cleanmovie.presentation.home.adapter.NowPlayingRecyclerAdapter
import com.e444er.cleanmovie.presentation.util.BaseMovieAndTvRecyclerAdapter
import com.e444er.cleanmovie.presentation.util.UiText
import com.e444er.cleanmovie.presentation.util.isErrorWithLoadState
import com.e444er.cleanmovie.presentation.util.isLoading
import okio.IOException

class HandlePagingLoadStates<T : Any>(
    nowPlayingRecyclerAdapter: NowPlayingRecyclerAdapter? = null,
    pagingAdapter: BaseMovieAndTvRecyclerAdapter<T>? = null,
    onLoading: () -> Unit,
    onNotLoading: () -> Unit,
    onError: (UiText) -> Unit
) {
    init {
        nowPlayingRecyclerAdapter?.addLoadStateListener { loadStates ->
            handlePagingLoadState(
                loadStates = loadStates,
                onLoading = onLoading,
                onNotLoading = onNotLoading,
                onError = onError
            )
        }
        pagingAdapter?.addLoadStateListener { loadStates ->
            handlePagingLoadState(
                loadStates = loadStates,
                onLoading = onLoading,
                onNotLoading = onNotLoading,
                onError = onError
            )
        }
    }

    private fun handlePagingLoadState(
        loadStates: CombinedLoadStates,
        onLoading: () -> Unit,
        onNotLoading: () -> Unit,
        onError: (UiText) -> Unit
    ) {
        if (loadStates.isLoading()) {
            onLoading()
        } else {
            onNotLoading()
        }
        loadStates.isErrorWithLoadState()?.let { loadStateError ->
            if (loadStateError.error is IOException) {
                onError(UiText.StringResource(R.string.internet_error))
            } else {
                onError(UiText.StringResource(R.string.error))
            }
        }
    }
}