package com.e444er.cleanmovie.feature_authentication.util

sealed class AuthError {
    object FieldEmpty : AuthError()
}
