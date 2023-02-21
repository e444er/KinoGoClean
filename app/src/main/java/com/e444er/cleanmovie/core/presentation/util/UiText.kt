package com.e444er.cleanmovie.core.presentation.util

import android.content.Context
import androidx.annotation.StringRes
import com.e444er.cleanmovie.R

sealed class UiText {
    data class DynamicText(val value: String) : UiText()
    data class StringResource(@StringRes val id: Int) : UiText()

    companion object {
        fun unknownError(): UiText {
            return StringResource(R.string.error)
        }
    }
}

fun UiText.asString(context: Context): String {
    return when (this) {
        is UiText.DynamicText -> this.value
        is UiText.StringResource -> context.getString(this.id)
    }
}
