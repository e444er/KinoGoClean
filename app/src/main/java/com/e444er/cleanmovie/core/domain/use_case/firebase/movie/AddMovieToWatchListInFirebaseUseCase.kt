package com.e444er.cleanmovie.core.domain.use_case.firebase.movie

import com.e444er.cleanmovie.R
import com.e444er.cleanmovie.core.domain.models.MovieWatchListItem
import com.e444er.cleanmovie.core.domain.repository.FirebaseCoreMovieRepository
import com.e444er.cleanmovie.core.domain.repository.FirebaseCoreRepository
import com.e444er.cleanmovie.core.presentation.util.UiText
import com.e444er.cleanmovie.core.util.Constants
import javax.inject.Inject

class AddMovieToWatchListInFirebaseUseCase @Inject constructor(
    private val firebaseCoreRepository: FirebaseCoreRepository,
    private val firebaseCoreMovieRepository: FirebaseCoreMovieRepository
) {

    operator fun invoke(
        moviesInWatchList: List<MovieWatchListItem>,
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit
    ) {
        val currentUser = firebaseCoreRepository.getCurrentUser()
        val userUid = currentUser?.uid
            ?: return onFailure(UiText.StringResource(R.string.must_login_able_to_add_in_list))

        val data = mapOf(
            Constants.FIREBASE_MOVIES_FIELD_NAME to moviesInWatchList
        )

        firebaseCoreMovieRepository.addMovieToWatchList(
            userUid = userUid,
            data = data,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }
}