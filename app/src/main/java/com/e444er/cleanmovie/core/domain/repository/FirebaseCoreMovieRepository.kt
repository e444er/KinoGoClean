package com.e444er.cleanmovie.core.domain.repository

import com.e444er.cleanmovie.core.domain.models.FavoriteMovie
import com.e444er.cleanmovie.core.domain.models.MovieWatchListItem
import com.e444er.cleanmovie.core.presentation.util.UiText

interface FirebaseCoreMovieRepository {
    fun addMovieToFavoriteList(
        userUid: String,
        data: Map<String, List<FavoriteMovie>>,
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit
    )

    fun addMovieToWatchList(
        userUid: String,
        data: Map<String, List<MovieWatchListItem>>,
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit
    )
}