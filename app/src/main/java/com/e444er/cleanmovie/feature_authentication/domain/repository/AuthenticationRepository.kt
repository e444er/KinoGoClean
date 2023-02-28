package com.e444er.cleanmovie.feature_authentication.domain.repository

import com.e444er.cleanmovie.core.presentation.util.UiText
import com.google.firebase.auth.AuthCredential

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

    fun signInWithCredential(
        credential: AuthCredential,
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit
    )
}