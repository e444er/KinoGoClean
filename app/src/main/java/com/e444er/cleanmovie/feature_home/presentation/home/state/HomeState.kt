package com.e444er.cleanmovie.feature_home.presentation.home.state

import com.e444er.cleanmovie.core.data.dto.Genre
import com.e444er.cleanmovie.core.presentation.util.UiText

data class HomeState(
    val isShowsSeeAllPage: Boolean = false,
    val seeAllPageToolBarText: UiText? = null,
    val countryIsoCode: String = "",
    val languageIsoCode: String = "",
    val movieGenreList: List<Genre> = emptyList(),
    val tvGenreList: List<Genre> = emptyList()
)
