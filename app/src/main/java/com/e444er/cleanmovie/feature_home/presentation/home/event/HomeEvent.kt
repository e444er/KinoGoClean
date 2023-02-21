package com.e444er.cleanmovie.feature_home.presentation.home.event

import com.e444er.cleanmovie.core.presentation.util.UiText
import com.e444er.cleanmovie.feature_home.presentation.home.HomeFragmentDirections

sealed class HomeEvent {
    data class ClickSeeAllText(val seeAllPageToolBarText: UiText) : HomeEvent()
    object NavigateUpFromSeeAllSection : HomeEvent()
    data class UpdateCountryIsoCode(val countryIsoCode: String) : HomeEvent()
    object OnBackPressed : HomeEvent()
    data class NavigateToDetailBottomSheet(val directions: HomeFragmentDirections.ActionHomeFragmentToDetailBottomSheet) :
        HomeEvent()
}
