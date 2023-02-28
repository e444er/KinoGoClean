package com.e444er.cleanmovie.core.domain.use_case.firebase.tv

import com.e444er.cleanmovie.core.domain.use_case.database.LocalDatabaseUseCases
import com.e444er.cleanmovie.core.domain.use_case.firebase.FirebaseCoreUseCases
import com.e444er.cleanmovie.core.presentation.util.UiText
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetTvSeriesWatchFromLocalDatabaseThenUpdateToFirebase @Inject constructor(
    private val localDatabaseUseCases: LocalDatabaseUseCases,
    private val firebaseCoreUseCases: FirebaseCoreUseCases
) {

    suspend operator fun invoke(
        onSuccess: () -> Unit,
        onFailure: (UiText) -> Unit
    ) {
        val tvSeriesIdsInWatchList =
            localDatabaseUseCases.getTvSeriesWatchListItemIdsUseCase().first()

        firebaseCoreUseCases.addTvSeriesToWatchListInFirebaseUseCase(
            tvSeriesIdsInWatchList = tvSeriesIdsInWatchList,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }
}