package com.e444er.cleanmovie.feature_authentication.domain.use_case

import com.e444er.cleanmovie.R
import com.e444er.cleanmovie.core.domain.repository.FirebaseCoreRepository
import com.e444er.cleanmovie.core.domain.repository.LocalDatabaseRepository
import com.e444er.cleanmovie.core.presentation.util.UiText
import com.e444er.cleanmovie.feature_authentication.domain.repository.FirebaseTvSeriesRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetFavoriteTvSeriesFromFirebaseThenUpdateLocalDatabaseUseCase @Inject constructor(
    private val firebaseCoreRepository: FirebaseCoreRepository,
    private val firebaseTvSeriesRepository: FirebaseTvSeriesRepository,
    private val localDatabaseRepository: LocalDatabaseRepository
) {

    operator fun invoke(
        onFailure: (uiText: UiText) -> Unit,
        coroutineScope: CoroutineScope
    ) {
        val currentUser = firebaseCoreRepository.getCurrentUser()
        val userUid = currentUser?.uid
            ?: return onFailure(UiText.StringResource(R.string.error_user))

        firebaseTvSeriesRepository.getFavoriteTvSeries(
            userUid = userUid,
            onSuccess = { favoriteOfListTvSeries ->
                favoriteOfListTvSeries.forEach { favoriteTvSeries ->
                    coroutineScope.launch {
                        localDatabaseRepository.insertTvSeriesToFavoriteList(favoriteTvSeries)
                    }
                }
            },
            onFailure = onFailure
        )
    }
}