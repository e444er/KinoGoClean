package com.e444er.cleanmovie.presentation.home

import com.e444er.cleanmovie.data.models.Genre
import com.e444er.cleanmovie.presentation.util.UiText

data class HomeState(
    val isShowsSeeAllPage: Boolean = false,
    val seeAllPageToolBarText: UiText? = null,
    val countryIsoCode: String = "",
    val languageIsoCode: String = "",
    val movieGenreList: List<Genre> = emptyList(),
    val tvGenreList: List<Genre> = emptyList()
)
