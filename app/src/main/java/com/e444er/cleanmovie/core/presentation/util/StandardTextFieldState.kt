package com.e444er.cleanmovie.core.presentation.util

import com.e444er.cleanmovie.feature_authentication.util.AuthError

data class StandardTextFieldState(
    val text: String = "",
    val error: AuthError? = null
)
