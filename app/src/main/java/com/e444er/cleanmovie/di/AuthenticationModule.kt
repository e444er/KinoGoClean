package com.e444er.cleanmovie.di

import com.e444er.cleanmovie.core.domain.repository.FirebaseCoreRepository
import com.e444er.cleanmovie.core.domain.repository.LocalDatabaseRepository
import com.e444er.cleanmovie.feature_authentication.data.repository.AuthenticaitonRepositoryImpl
import com.e444er.cleanmovie.feature_authentication.data.repository.FirebaseMovieRepositoryImpl
import com.e444er.cleanmovie.feature_authentication.data.repository.FirebaseTvSeriesRepositoryImpl
import com.e444er.cleanmovie.feature_authentication.domain.repository.AuthenticationRepository
import com.e444er.cleanmovie.feature_authentication.domain.repository.FirebaseMovieRepository
import com.e444er.cleanmovie.feature_authentication.domain.repository.FirebaseTvSeriesRepository
import com.e444er.cleanmovie.feature_authentication.domain.use_case.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthenticationModule {

    @Provides
    @Singleton
    fun provideAuthenticationRepository(
        firebaseAuth: FirebaseAuth
    ): AuthenticationRepository {
        return AuthenticaitonRepositoryImpl(firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideLoginEmailAndPasswordUseCase(
        repository: AuthenticationRepository
    ): SignInWithEmailAndPasswordUseCase {
        return SignInWithEmailAndPasswordUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideCreateEmailAndPasswordUseCase(
        repository: AuthenticationRepository
    ): CreateUserWithEmailAndPasswordUseCase {
        return CreateUserWithEmailAndPasswordUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideFirebaseMovieRepository(
        firestore: FirebaseFirestore
    ): FirebaseMovieRepository {
        return FirebaseMovieRepositoryImpl(firestore)
    }

    @Provides
    @Singleton
    fun provideFirebaseTvSeriesRepository(
        firestore: FirebaseFirestore
    ): FirebaseTvSeriesRepository {
        return FirebaseTvSeriesRepositoryImpl(firestore)
    }

    @Provides
    @Singleton
    fun provideFirebaseUseCases(
        firebaseMovieRepository: FirebaseMovieRepository,
        firebaseTvSeriesRepository: FirebaseTvSeriesRepository,
        firebaseCoreRepository: FirebaseCoreRepository,
        localDatabaseRepository: LocalDatabaseRepository
    ): FirebaseUseCases {
        return FirebaseUseCases(
            getFavoriteMovieFromFirebaseThenUpdateLocalDatabaseUseCase = GetFavoriteMovieFromFirebaseThenUpdateLocalDatabaseUseCase(
                firebaseCoreRepository = firebaseCoreRepository,
                firebaseMovieRepository = firebaseMovieRepository,
                localDatabaseRepository = localDatabaseRepository
            ),
            getMovieWatchListFromFirebaseThenUpdateLocalDatabaseUseCase = GetMovieWatchListFromFirebaseThenUpdateLocalDatabaseUseCase(
                firebaseCoreRepository = firebaseCoreRepository,
                firebaseMovieRepository = firebaseMovieRepository,
                localDatabaseRepository = localDatabaseRepository
            ),
            getFavoriteTvSeriesFromFirebaseThenUpdateLocalDatabaseUseCase = GetFavoriteTvSeriesFromFirebaseThenUpdateLocalDatabaseUseCase(
                firebaseCoreRepository = firebaseCoreRepository,
                firebaseTvSeriesRepository = firebaseTvSeriesRepository,
                localDatabaseRepository = localDatabaseRepository
            ),
            getTvSeriesWatchListFromFirebaseThenUpdateLocalDatabaseUseCase = GetTvSeriesWatchListFromFirebaseThenUpdateLocalDatabaseUseCase(
                firebaseCoreRepository = firebaseCoreRepository,
                firebaseTvSeriesRepository = firebaseTvSeriesRepository,
                localDatabaseRepository = localDatabaseRepository
            )
        )
    }
}