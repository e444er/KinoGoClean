package com.e444er.cleanmovie.feature_authentication.presentation.util

import android.content.Context
import com.e444er.cleanmovie.R
import com.e444er.cleanmovie.feature_authentication.util.AuthError
import com.google.android.material.textfield.TextInputLayout

class AuthUtil {
    companion object {
        fun updateFieldEmptyErrorInTextInputLayout(
            textInputLayout: TextInputLayout,
            context: Context,
            authError: AuthError?
        ) {
            textInputLayout.isErrorEnabled = authError != null
            when (authError) {
                is AuthError.FieldEmpty -> {
                    textInputLayout.error = context.getString(R.string.error_field_empty)
                }
                null -> return
            }
        }
        const val EMAIL_ARGUMENT_NAME = "email"
    }
}