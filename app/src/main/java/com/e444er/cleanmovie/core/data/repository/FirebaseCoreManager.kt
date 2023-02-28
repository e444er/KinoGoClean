package com.e444er.cleanmovie.core.data.repository

import com.e444er.cleanmovie.core.domain.repository.FirebaseCoreMovieRepository
import com.e444er.cleanmovie.core.domain.repository.FirebaseCoreRepository
import com.e444er.cleanmovie.core.domain.repository.FirebaseCoreTvSeriesRepository


data class FirebaseCoreManager(
    val firebaseCoreRepository: FirebaseCoreRepository,
    val firebaseCoreMovieRepository: FirebaseCoreMovieRepository,
    val firebaseCoreTvSeriesRepository: FirebaseCoreTvSeriesRepository
)
