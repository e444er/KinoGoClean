package com.e444er.cleanmovie.feature_authentication.domain.use_case

import com.e444er.cleanmovie.R
import com.e444er.cleanmovie.core.domain.repository.FirebaseCoreRepository
import com.e444er.cleanmovie.core.domain.repository.LocalDatabaseRepository
import com.e444er.cleanmovie.core.presentation.util.UiText
import com.e444er.cleanmovie.feature_authentication.domain.repository.FirebaseMovieRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GetFavoriteMovieFromFirebaseThenUpdateLocalDatabaseUseCase @AssistedInject constructor(
    private val firebaseCoreRepository: FirebaseCoreRepository,
    private val firebaseMovieRepository: FirebaseMovieRepository,
    private val localDatabaseRepository: LocalDatabaseRepository,
    @Assisted private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) {

    operator fun invoke(
        onFailure: (uiText: UiText) -> Unit
    ) {
        val currentUser = firebaseCoreRepository.getCurrentUser()
        val userUid = currentUser?.uid
            ?: return onFailure(UiText.StringResource(R.string.error_user))

        firebaseMovieRepository.getFavoriteMovie(
            userUid = userUid,
            onSuccess = { favoriteMovies ->
                favoriteMovies.forEach { favoriteMovie ->
                    coroutineScope.launch {
                        localDatabaseRepository.insertMovieToFavoriteList(favoriteMovie)
                    }
                }
            },
            onFailure = onFailure
        )
    }
}