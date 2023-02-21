package com.e444er.cleanmovie.core.presentation.util

import androidx.navigation.NavDirections

sealed class UiEvent {
    data class NavigateTo(val directions: NavDirections) : UiEvent()
}
