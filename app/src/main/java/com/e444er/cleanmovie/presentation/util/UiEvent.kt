package com.e444er.cleanmovie.presentation.util

import androidx.navigation.NavDirections

sealed class UiEvent {
    data class NavigateTo(val directions: NavDirections) : UiEvent()
}
