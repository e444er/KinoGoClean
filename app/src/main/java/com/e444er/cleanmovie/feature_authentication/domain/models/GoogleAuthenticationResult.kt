package com.e444er.cleanmovie.feature_authentication.domain.models

import com.e444er.cleanmovie.core.presentation.util.UiText

data class GoogleAuthenticationResult(
    val errorMessage: UiText? = null,
    val result: Unit? = null
)