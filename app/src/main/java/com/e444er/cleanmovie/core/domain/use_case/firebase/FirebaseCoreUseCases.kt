package com.e444er.cleanmovie.core.domain.use_case.firebase

import com.e444er.cleanmovie.core.domain.use_case.IsUserSignInUseCase
import com.e444er.cleanmovie.core.domain.use_case.firebase.movie.AddMovieToFavoriteListInFirebaseUseCase
import com.e444er.cleanmovie.core.domain.use_case.firebase.movie.AddMovieToWatchListInFirebaseUseCase
import com.e444er.cleanmovie.core.domain.use_case.firebase.tv.AddTvSeriesToFavoriteListInFirebaseUseCase
import com.e444er.cleanmovie.core.domain.use_case.firebase.tv.AddTvSeriesToWatchListInFirebaseUseCase

data class FirebaseCoreUseCases(
    val getCurrentUserUseCase: GetCurrentUserUseCase,
    val signOutUseCase: SignOutUseCase,
    val isUserSignInUseCase: IsUserSignInUseCase,
    val addMovieToFavoriteListInFirebaseUseCase: AddMovieToFavoriteListInFirebaseUseCase,
    val addMovieToWatchListInFirebaseUseCase: AddMovieToWatchListInFirebaseUseCase,
    val addTvSeriesToFavoriteListInFirebaseUseCase: AddTvSeriesToFavoriteListInFirebaseUseCase,
    val addTvSeriesToWatchListInFirebaseUseCase: AddTvSeriesToWatchListInFirebaseUseCase
)
