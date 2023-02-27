package com.e444er.cleanmovie.core.presentation.detail_bottom_sheet

import androidx.navigation.NavDirections
import com.e444er.cleanmovie.core.presentation.util.UiText

sealed class DetailBottomUiEvent {
    data class NavigateTo(val directions: NavDirections) : DetailBottomUiEvent()
    data class ShowSnackbar(val uiText: UiText) : DetailBottomUiEvent()
    object PopBackStack : DetailBottomUiEvent()
    object ShowAlertDialog : DetailBottomUiEvent()
}
