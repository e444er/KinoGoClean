package com.e444er.cleanmovie.di

import com.e444er.cleanmovie.core.domain.repository.DataStoreOperations
import com.e444er.cleanmovie.core.domain.use_case.GetLanguageIsoCodeUseCase
import com.e444er.cleanmovie.feature_person_detail.data.remote.PersonApi
import com.e444er.cleanmovie.feature_person_detail.data.repository.PersonRepositoryImpl
import com.e444er.cleanmovie.feature_person_detail.domain.repository.PersonRepository
import com.e444er.cleanmovie.feature_person_detail.domain.use_case.GetPersonDetailUseCase
import com.e444er.cleanmovie.feature_person_detail.domain.use_case.PersonDetailUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersonModule {

    @Provides
    @Singleton
    fun providePersonApi(retrofit: Retrofit): PersonApi {
        return retrofit.create(PersonApi::class.java)
    }

    @Provides
    @Singleton
    fun providesPersonRepository(personApi: PersonApi): PersonRepository {
        return PersonRepositoryImpl(personApi)
    }

    @Provides
    @Singleton
    fun providesPersonDetailUseCase(
        repository: PersonRepository,
        dataStoreOperations: DataStoreOperations
    ): PersonDetailUseCases {
        return PersonDetailUseCases(
            getPersonDetailUseCase = GetPersonDetailUseCase(repository),
            getLanguageIsoCodeUseCase = GetLanguageIsoCodeUseCase(dataStoreOperations)
        )
    }
}