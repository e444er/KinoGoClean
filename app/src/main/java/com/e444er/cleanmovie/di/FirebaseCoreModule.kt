package com.e444er.cleanmovie.di

import com.e444er.cleanmovie.core.data.repository.FirebaseCoreManager
import com.e444er.cleanmovie.core.data.repository.FirebaseCoreMovieRepositoryImpl
import com.e444er.cleanmovie.core.data.repository.FirebaseCoreRepositoryImpl
import com.e444er.cleanmovie.core.data.repository.FirebaseCoreTvSeriesRepositoryImpl
import com.e444er.cleanmovie.core.domain.repository.FirebaseCoreMovieRepository
import com.e444er.cleanmovie.core.domain.repository.FirebaseCoreRepository
import com.e444er.cleanmovie.core.domain.repository.FirebaseCoreTvSeriesRepository
import com.e444er.cleanmovie.core.domain.use_case.IsUserSignInUseCase
import com.e444er.cleanmovie.core.domain.use_case.firebase.FirebaseCoreUseCases
import com.e444er.cleanmovie.core.domain.use_case.firebase.GetCurrentUserUseCase
import com.e444er.cleanmovie.core.domain.use_case.firebase.SignOutUseCase
import com.e444er.cleanmovie.core.domain.use_case.firebase.movie.AddMovieToFavoriteListInFirebaseUseCase
import com.e444er.cleanmovie.core.domain.use_case.firebase.movie.AddMovieToWatchListInFirebaseUseCase
import com.e444er.cleanmovie.core.domain.use_case.firebase.tv.AddTvSeriesToFavoriteListInFirebaseUseCase
import com.e444er.cleanmovie.core.domain.use_case.firebase.tv.AddTvSeriesToWatchListInFirebaseUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseCoreModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseCoreRepository(
        auth: FirebaseAuth
    ): FirebaseCoreRepository {
        return FirebaseCoreRepositoryImpl(auth)
    }

    @Provides
    @Singleton
    fun provideFirebaseCoreMovieRepository(
        firestore: FirebaseFirestore
    ): FirebaseCoreMovieRepository {
        return FirebaseCoreMovieRepositoryImpl(firestore)
    }

    @Provides
    @Singleton
    fun provideFirebaseCoreTvSeriesRepository(
        firestore: FirebaseFirestore
    ): FirebaseCoreTvSeriesRepository {
        return FirebaseCoreTvSeriesRepositoryImpl(firestore)
    }

    @Provides
    @Singleton
    fun provideFirebaseCoreManager(
        firebaseCoreRepository: FirebaseCoreRepository,
        firebaseCoreMovieRepository: FirebaseCoreMovieRepository,
        firebaseCoreTvSeriesRepository: FirebaseCoreTvSeriesRepository
    ): FirebaseCoreManager {
        return FirebaseCoreManager(
            firebaseCoreRepository = firebaseCoreRepository,
            firebaseCoreMovieRepository = firebaseCoreMovieRepository,
            firebaseCoreTvSeriesRepository = firebaseCoreTvSeriesRepository
        )
    }

    @Provides
    @Singleton
    fun provideFirebaseCoreUseCases(
        firebaseCoreManager: FirebaseCoreManager
    ): FirebaseCoreUseCases {
        return FirebaseCoreUseCases(
            getCurrentUserUseCase = GetCurrentUserUseCase(firebaseCoreManager.firebaseCoreRepository),
            signOutUseCase = SignOutUseCase(firebaseCoreManager.firebaseCoreRepository),
            isUserSignInUseCase = IsUserSignInUseCase(firebaseCoreManager.firebaseCoreRepository),
            addMovieToFavoriteListInFirebaseUseCase = AddMovieToFavoriteListInFirebaseUseCase(
                firebaseCoreRepository = firebaseCoreManager.firebaseCoreRepository,
                firebaseCoreMovieRepository = firebaseCoreManager.firebaseCoreMovieRepository
            ),
            addMovieToWatchListInFirebaseUseCase = AddMovieToWatchListInFirebaseUseCase(
                firebaseCoreRepository = firebaseCoreManager.firebaseCoreRepository,
                firebaseCoreMovieRepository = firebaseCoreManager.firebaseCoreMovieRepository
            ),
            addTvSeriesToFavoriteListInFirebaseUseCase = AddTvSeriesToFavoriteListInFirebaseUseCase(
                firebaseCoreRepository = firebaseCoreManager.firebaseCoreRepository,
                firebaseCoreTvSeriesRepository = firebaseCoreManager.firebaseCoreTvSeriesRepository
            ),
            addTvSeriesToWatchListInFirebaseUseCase = AddTvSeriesToWatchListInFirebaseUseCase(
                firebaseCoreRepository = firebaseCoreManager.firebaseCoreRepository,
                firebaseCoreTvSeriesRepository = firebaseCoreManager.firebaseCoreTvSeriesRepository
            )
        )
    }
}