package com.e444er.cleanmovie.di

import com.e444er.cleanmovie.core.domain.repository.DataStoreOperations
import com.e444er.cleanmovie.core.domain.repository.RemoteRepository
import com.e444er.cleanmovie.core.domain.use_case.GetLanguageIsoCodeUseCase
import com.e444er.cleanmovie.core.domain.use_case.GetMovieGenreListUseCase
import com.e444er.cleanmovie.core.domain.use_case.GetTvGenreListUseCase
import com.e444er.cleanmovie.feature_explore.data.remote.ExploreApi
import com.e444er.cleanmovie.feature_explore.data.repository.ExploreRepositoryImpl
import com.e444er.cleanmovie.feature_explore.domain.repository.ExploreRepository
import com.e444er.cleanmovie.feature_explore.domain.use_case.DiscoverMovieUseCase
import com.e444er.cleanmovie.feature_explore.domain.use_case.DiscoverTvUseCase
import com.e444er.cleanmovie.feature_explore.domain.use_case.ExploreUseCases
import com.e444er.cleanmovie.feature_explore.domain.use_case.MultiSearchUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ExploreModule {

    @Provides
    @Singleton
    fun provideExploreApi(retrofit: Retrofit): ExploreApi {
        return retrofit.create(ExploreApi::class.java)
    }

    @Provides
    @Singleton
    fun provideExploreRepository(
        exploreApi: ExploreApi
    ): ExploreRepository {
        return ExploreRepositoryImpl(exploreApi)
    }

    @Provides
    @Singleton
    fun provideExploreUseCases(
        exploreRepository: ExploreRepository,
        remoteRepository: RemoteRepository,
        dataStoreOperations: DataStoreOperations
    ): ExploreUseCases {
        return ExploreUseCases(
            tvGenreListUseCase = GetTvGenreListUseCase(remoteRepository),
            movieGenreListUseCase = GetMovieGenreListUseCase(remoteRepository),
            getLanguageIsoCodeUseCase = GetLanguageIsoCodeUseCase(dataStoreOperations),
            discoverMovieUseCase = DiscoverMovieUseCase(
                exploreRepository,
                GetMovieGenreListUseCase(remoteRepository)
            ),
            discoverTvUseCase = DiscoverTvUseCase(
                exploreRepository,
                GetTvGenreListUseCase(remoteRepository)
            ),
            multiSearchUseCase = MultiSearchUseCase(
                exploreRepository,
                GetMovieGenreListUseCase(remoteRepository),
                GetTvGenreListUseCase(remoteRepository)
            )
        )
    }
}