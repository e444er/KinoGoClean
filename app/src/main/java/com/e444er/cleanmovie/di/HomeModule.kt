package com.e444er.cleanmovie.di

import com.e444er.cleanmovie.core.domain.repository.DataStoreOperations
import com.e444er.cleanmovie.core.domain.repository.RemoteRepository
import com.e444er.cleanmovie.core.domain.use_case.GetLanguageIsoCodeUseCase
import com.e444er.cleanmovie.core.domain.use_case.GetMovieGenreListUseCase
import com.e444er.cleanmovie.core.domain.use_case.GetTvGenreListUseCase
import com.e444er.cleanmovie.core.domain.use_case.UpdateLanguageIsoCodeUseCase
import com.e444er.cleanmovie.feature_home.data.repository.HomeRepositoryImpl
import com.e444er.cleanmovie.feature_home.data_source.remote.HomeApi
import com.e444er.cleanmovie.feature_home.domain.repository.HomeRepository
import com.e444er.cleanmovie.feature_home.domain.use_cases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeModule {

    @Provides
    @Singleton
    fun provideHomeApi(retrofit: Retrofit): HomeApi {
        return retrofit.create(HomeApi::class.java)
    }

    @Provides
    @Singleton
    fun provideHomeRepository(
        homeApi: HomeApi
    ): HomeRepository {
        return HomeRepositoryImpl(homeApi)
    }

    @Provides
    @Singleton
    fun provideHomeUseCases(
        homeRepository: HomeRepository,
        dataStoreOperations: DataStoreOperations,
        remoteRepository: RemoteRepository
    ): HomeUseCases {
        return HomeUseCases(
            getMovieGenreList = GetMovieGenreListUseCase(remoteRepository),
            getTvGenreList = GetTvGenreListUseCase(remoteRepository),
            getNowPlayingMoviesUseCase = GetNowPlayingMoviesUseCase(homeRepository),
            getLanguageIsoCodeUseCase = GetLanguageIsoCodeUseCase(dataStoreOperations),
            getPopularMoviesUseCase = GetPopularMoviesUseCase(homeRepository),
            getTopRatedMoviesUseCase = GetTopRatedMoviesUseCase(homeRepository),
            getPopularTvSeriesUseCase = GetPopularTvSeriesUseCase(homeRepository),
            getTopRatedTvSeriesUseCase = GetTopRatedTvSeriesUseCase(homeRepository = homeRepository),
            updateLanguageIsoCodeUseCase = UpdateLanguageIsoCodeUseCase(dataStoreOperations = dataStoreOperations)
        )
    }
}