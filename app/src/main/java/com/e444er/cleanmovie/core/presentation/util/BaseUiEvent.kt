package com.e444er.cleanmovie.core.presentation.util

import androidx.navigation.NavDirections

sealed class BaseUiEvent {
    data class NavigateTo(val directions: NavDirections) : BaseUiEvent()
    data class ShowSnackbar(val uiText: UiText) : BaseUiEvent()
}

sealed class UiEvent : BaseUiEvent() {
    data class NavigateTo(val directions: NavDirections) : UiEvent()
    data class ShowSnackbar(val uiText: UiText) : UiEvent()
    object PopBackStack : UiEvent()
}
