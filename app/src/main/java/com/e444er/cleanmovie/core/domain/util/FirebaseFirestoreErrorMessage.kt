package com.e444er.cleanmovie.core.domain.util

import com.e444er.cleanmovie.R


class FirebaseFirestoreErrorMessage {

    companion object {

        fun getMessage(
            errorCode: String
        ): Int {
            var errorStringId = R.string.error
            errorMessages.forEach { (t, u) ->
                if (errorCode == t) {
                    errorStringId = u
                }
            }
            return errorStringId
        }

        private val errorMessages = mapOf(
            "PERMISSION_DENIED" to R.string.permission_denied_error,
            "NOT_FOUND" to R.string.not_found_error,
            "ALREADY_EXISTS" to R.string.already_exists_error,
            "ABORTED" to R.string.aborted_error,
            "UNAVAILABLE" to R.string.unavailable_error
        )
    }
}