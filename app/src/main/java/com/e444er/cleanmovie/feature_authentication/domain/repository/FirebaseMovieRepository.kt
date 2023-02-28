package com.e444er.cleanmovie.feature_authentication.domain.repository

import com.e444er.cleanmovie.core.domain.models.FavoriteMovie
import com.e444er.cleanmovie.core.domain.models.MovieWatchListItem
import com.e444er.cleanmovie.core.presentation.util.UiText


interface FirebaseMovieRepository {

    fun getFavoriteMovie(
        userUid: String,
        onSuccess: (List<FavoriteMovie>) -> Unit,
        onFailure: (uiText: UiText) -> Unit
    )

    fun getMovieInWatchList(
        userUid: String,
        onSuccess: (List<MovieWatchListItem>) -> Unit,
        onFailure: (uiText: UiText) -> Unit
    )
}