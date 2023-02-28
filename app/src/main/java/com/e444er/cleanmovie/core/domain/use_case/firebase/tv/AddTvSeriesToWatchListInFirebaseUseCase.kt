package com.e444er.cleanmovie.core.domain.use_case.firebase.tv

import com.e444er.cleanmovie.R
import com.e444er.cleanmovie.core.domain.models.TvSeriesWatchListItem
import com.e444er.cleanmovie.core.domain.repository.FirebaseCoreRepository
import com.e444er.cleanmovie.core.domain.repository.FirebaseCoreTvSeriesRepository
import com.e444er.cleanmovie.core.presentation.util.UiText
import com.e444er.cleanmovie.core.util.Constants
import javax.inject.Inject

class AddTvSeriesToWatchListInFirebaseUseCase @Inject constructor(
    private val firebaseCoreRepository: FirebaseCoreRepository,
    private val firebaseCoreTvSeriesRepository: FirebaseCoreTvSeriesRepository
) {

    operator fun invoke(
        tvSeriesInWatchList: List<TvSeriesWatchListItem>,
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit
    ) {
        val currentUser = firebaseCoreRepository.getCurrentUser()
        val userUid = currentUser?.uid
            ?: return onFailure(UiText.StringResource(R.string.must_login_able_to_add_in_list))

        val data = mapOf(
            Constants.FIREBASE_TV_SERIES_FIELD_NAME to tvSeriesInWatchList
        )

        firebaseCoreTvSeriesRepository.addTvSeriesToWatchList(
            userUid = userUid,
            data = data,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }
}