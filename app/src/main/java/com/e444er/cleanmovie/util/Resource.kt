package com.e444er.cleanmovie.util

import com.e444er.cleanmovie.presentation.util.UiText

sealed class Resource<T>(val data: T? = null, val uiText: UiText? = null) {
    class Success<T>(data: T) : Resource<T>(data = data)
    class Error<T>(uiText: UiText, data: T? = null) : Resource<T>(data = data, uiText = uiText)
}