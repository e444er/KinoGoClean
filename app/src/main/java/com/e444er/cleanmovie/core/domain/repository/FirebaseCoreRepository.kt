package com.e444er.cleanmovie.core.domain.repository

import com.e444er.cleanmovie.core.presentation.util.UiText
import com.google.firebase.auth.FirebaseUser

interface FirebaseCoreRepository {

    fun getCurrentUser(): FirebaseUser?
    fun signOut()

    fun addMovieToFavoriteList(
        userUid: String,
        data: Map<String, List<Int>>,
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit
    )

    fun addMovieToWatchList(
        userUid: String,
        data: Map<String, List<Int>>,
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit
    )

    fun addTvSeriesToFavoriteList(
        userUid: String,
        data: Map<String, List<Int>>,
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit

    )

    fun addTvSeriesToWatchList(
        userUid: String,
        data: Map<String, List<Int>>,
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit
    )
}