package com.e444er.cleanmovie.feature_authentication.domain.repository

import com.e444er.cleanmovie.core.presentation.util.UiText

interface AuthenticationRepository {

    fun signInWithEmailAndPassword(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit
    )

    fun createWithEmailAndPassword(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit
    )

    fun sendPasswordResetEmail(
        email: String,
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit
    )
}