package com.e444er.cleanmovie.di

import com.e444er.cleanmovie.core.data.repository.FirebaseCoreRepositoryImpl
import com.e444er.cleanmovie.core.domain.repository.FirebaseCoreRepository
import com.e444er.cleanmovie.core.domain.use_case.FirebaseCoreUseCases
import com.e444er.cleanmovie.core.domain.use_case.GetCurrentUserUseCase
import com.e444er.cleanmovie.core.domain.use_case.IsUserSignInUseCase
import com.e444er.cleanmovie.core.domain.use_case.SignOutUseCase
import com.e444er.cleanmovie.feature_authentication.data.repository.AuthenticaitonRepositoryImpl
import com.e444er.cleanmovie.feature_authentication.domain.repository.AuthenticationRepository
import com.e444er.cleanmovie.feature_authentication.domain.use_case.CreateUserWithEmailAndPasswordUseCase
import com.e444er.cleanmovie.feature_authentication.domain.use_case.SignInWithEmailAndPasswordUseCase
import com.google.firebase.auth.FirebaseAuth
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
        auth: FirebaseAuth
    ): FirebaseCoreRepository {
        return FirebaseCoreRepositoryImpl(auth)
    }

    @Provides
    @Singleton
    fun provideFirebaseCoreUseCases(
        repository: FirebaseCoreRepository
    ): FirebaseCoreUseCases {
        return FirebaseCoreUseCases(
            getCurrentUserUseCase = GetCurrentUserUseCase(repository),
            signOutUseCase = SignOutUseCase(repository),
            isUserSignInUseCase = IsUserSignInUseCase(repository)
        )
    }
}