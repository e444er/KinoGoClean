package com.e444er.cleanmovie.feature_authentication.domain.use_case

import com.e444er.cleanmovie.core.presentation.util.UiText
import com.e444er.cleanmovie.feature_authentication.domain.models.AuthenticationResult
import com.e444er.cleanmovie.feature_authentication.domain.repository.AuthenticationRepository
import com.e444er.cleanmovie.feature_authentication.util.AuthError
import javax.inject.Inject

class CreateUserWithEmailAndPasswordUseCase @Inject constructor(
    private val repository: AuthenticationRepository
) {

    operator fun invoke(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit
    ): AuthenticationResult {

        val emailError = if (email.isBlank()) AuthError.FieldEmpty else null
        val passwordError = if (password.isBlank()) AuthError.FieldEmpty else null

        if (emailError != null || passwordError != null) {
            return AuthenticationResult(
                emailError = emailError,
                passwordError = passwordError
            )
        }

        return AuthenticationResult(
            result = repository.createWithEmailAndPassword(
                email = email,
                password = password,
                onSuccess = onSuccess,
                onFailure = onFailure
            )
        )
    }
}