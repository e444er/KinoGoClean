package com.e444er.cleanmovie.util

import com.e444er.cleanmovie.BuildConfig

object Constants {
    const val BASE_URL ="https://api.themoviedb.org/3/"
    const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"

    const val DEFAULT_LANGUAGE = "en"
    const val DEFAULT_REGION = "US"
    const val STARTING_PAGE = 1

    const val API_KEY = BuildConfig.API_KEY

    const val ITEMS_PER_PAGE = 20

    const val PREFERENCES_NAME="mova_preferences_name"

    const val LOCALE_KEY ="locale_key"

    const val DISCOVER_DATE_QUERY_FOR_TV = "first_air_date"

    val supportedLanguages = listOf(
        "EN",
        "DE",
        "FR",
        "TR",
        "RU"
    )
}