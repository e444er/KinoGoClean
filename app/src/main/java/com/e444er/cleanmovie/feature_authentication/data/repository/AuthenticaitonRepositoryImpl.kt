package com.e444er.cleanmovie.feature_authentication.data.repository

import com.e444er.cleanmovie.core.presentation.util.UiText
import com.e444er.cleanmovie.feature_authentication.domain.repository.AuthenticationRepository
import com.e444er.cleanmovie.feature_authentication.domain.util.FirebaseAuthMessage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import javax.inject.Inject

class AuthenticaitonRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthenticationRepository {

    override fun signInWithEmailAndPassword(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit
    ) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                if (exception is FirebaseAuthException) {
                    val errorCode = exception.errorCode
                    val errorStringId = FirebaseAuthMessage.getMessage(errorCode)
                    onFailure(UiText.StringResource(errorStringId))
                } else {
                    onFailure(UiText.unknownError())
                }
            }
    }

    override fun createWithEmailAndPassword(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit
    ) {
        firebaseAuth.createUserWithEmailAndPassword(
            email, password
        ).addOnSuccessListener {
            onSuccess()
        }.addOnFailureListener { exception ->
            if (exception is FirebaseAuthException) {
                val errorCode = exception.errorCode
                val errorStringId = FirebaseAuthMessage.getMessage(errorCode = errorCode)
                onFailure(UiText.StringResource(errorStringId))
            } else {
                onFailure(UiText.unknownError())
            }
        }
    }
}