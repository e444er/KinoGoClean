package com.e444er.cleanmovie.presentation.home.event

import com.e444er.cleanmovie.presentation.home.HomeFragmentDirections
import com.e444er.cleanmovie.presentation.util.UiText

sealed class HomeEvent {
    data class ClickSeeAllText(val seeAllPageToolBarText: UiText) : HomeEvent()
    object NavigateUpFromSeeAllSection : HomeEvent()
    data class UpdateCountryIsoCode(val countryIsoCode: String) : HomeEvent()
    object OnBackPressed : HomeEvent()
    data class NavigateToDetailBottomSheet(val directions: HomeFragmentDirections.ActionHomeFragmentToDetailBottomSheetFragment) :
        HomeEvent()
}
