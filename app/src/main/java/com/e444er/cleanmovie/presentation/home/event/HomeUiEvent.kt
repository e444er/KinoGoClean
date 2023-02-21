package com.e444er.cleanmovie.presentation.home.event

import androidx.navigation.NavDirections
import com.e444er.cleanmovie.presentation.util.UiText

sealed class HomeUiEvent {
    data class NavigateTo(val directions: NavDirections) : HomeUiEvent()
    data class ShowSnackbar(val uiText: UiText) : HomeUiEvent()
    object RetryAllAdapters : HomeUiEvent()
}