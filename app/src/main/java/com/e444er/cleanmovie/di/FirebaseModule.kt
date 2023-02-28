package com.e444er.cleanmovie.di

import com.e444er.cleanmovie.core.data.repository.FirebaseCoreRepositoryImpl
import com.e444er.cleanmovie.core.domain.repository.FirebaseCoreRepository
import com.e444er.cleanmovie.core.domain.use_case.*
import com.e444er.cleanmovie.feature_authentication.data.repository.AuthenticaitonRepositoryImpl
import com.e444er.cleanmovie.feature_authentication.domain.repository.AuthenticationRepository
import com.e444er.cleanmovie.feature_authentication.domain.use_case.CreateUserWithEmailAndPasswordUseCase
import com.e444er.cleanmovie.feature_authentication.domain.use_case.SignInWithEmailAndPasswordUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
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
    fun provideFirebaseCoreRepository(
        auth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): FirebaseCoreRepository {
        return FirebaseCoreRepositoryImpl(auth, firestore)
    }

    @Provides
    @Singleton
    fun provideFirebaseCoreUseCases(
        repository: FirebaseCoreRepository
    ): FirebaseCoreUseCases {
        return FirebaseCoreUseCases(
            getCurrentUserUseCase = GetCurrentUserUseCase(repository),
            signOutUseCase = SignOutUseCase(repository),
            isUserSignInUseCase = IsUserSignInUseCase(repository),
            addMovieToFavoriteListInFirebaseUseCase = AddMovieToFavoriteListInFirebaseUseCase(
                repository
            ),
            addMovieToWatchListInFirebaseUseCase = AddMovieToWatchListInFirebaseUseCase(repository),
            addTvSeriesToFavoriteListInFirebaseUseCase = AddTvSeriesToFavoriteListInFirebaseUseCase(
                repository
            ),
            addTvSeriesToWatchListInFirebaseUseCase = AddTvSeriesToWatchListInFirebaseUseCase(
                repository
            )
        )
    }
}