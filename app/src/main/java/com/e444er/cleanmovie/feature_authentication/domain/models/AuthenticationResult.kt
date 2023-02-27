package com.e444er.cleanmovie.feature_authentication.domain.models

import com.e444er.cleanmovie.feature_authentication.util.AuthError

data class AuthenticationResult(
    val emailError: AuthError? = null,
    val passwordError: AuthError? = null,
    val result: Unit? = null
)