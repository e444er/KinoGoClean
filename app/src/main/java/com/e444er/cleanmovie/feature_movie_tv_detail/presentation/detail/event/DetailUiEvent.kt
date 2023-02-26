package com.e444er.cleanmovie.feature_movie_tv_detail.presentation.detail.event

import androidx.navigation.NavDirections
import com.e444er.cleanmovie.core.presentation.util.UiText

sealed class DetailUiEvent {
    data class ShowSnackbar(val uiText: UiText) : DetailUiEvent()
    object PopBackStack : DetailUiEvent()
    data class IntentToImdbWebSite(val url: String) : DetailUiEvent()
    data class NavigateTo(val directions: NavDirections) :
        DetailUiEvent()

}