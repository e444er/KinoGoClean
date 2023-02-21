package com.e444er.cleanmovie.presentation.detail_fragment.event

import com.e444er.cleanmovie.presentation.util.UiText


sealed class DetailUiEvent {
    data class ShowSnackbar(val uiText: UiText) : DetailUiEvent()
    object NavigateUp : DetailUiEvent()
    data class IntentToImdbWebSite(val url: String) : DetailUiEvent()

}