package com.e444er.cleanmovie.core.domain.use_case

import com.e444er.cleanmovie.R
import com.e444er.cleanmovie.core.domain.repository.FirebaseCoreRepository
import com.e444er.cleanmovie.core.presentation.util.UiText
import com.e444er.cleanmovie.core.util.Constants.FIREBASE_MOVIE_IDS_FIELD_NAME
import javax.inject.Inject

class AddMovieToWatchListInFirebaseUseCase @Inject constructor(
    private val repository: FirebaseCoreRepository
) {

    operator fun invoke(
        movieIdsInWatchList: List<Int>,
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit
    ) {
        val currentUser = repository.getCurrentUser()
        val userUid = currentUser?.uid
            ?: return onFailure(UiText.StringResource(R.string.must_login_able_to_add_in_list))

        val data = mapOf(
            FIREBASE_MOVIE_IDS_FIELD_NAME to movieIdsInWatchList
        )

        repository.addMovieToWatchList(
            userUid = userUid,
            data = data,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }
}