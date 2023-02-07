package com.e444er.cleanmovie.domain.models

import androidx.annotation.StringRes
import com.e444er.cleanmovie.R

data class Language(
    @StringRes val textId: Int,
    val iso: String
)

val supportedLanguages = mutableListOf<Language>(
    Language(R.string.language_english, "en"),
    Language(R.string.language_turkish, "tr"),
    Language(R.string.language_spanish, "es"),
    Language(R.string.language_german, "de"),
    Language(R.string.language_russian, "ru"),
)
