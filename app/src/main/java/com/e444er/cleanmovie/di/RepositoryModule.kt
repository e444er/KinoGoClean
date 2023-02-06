package com.e444er.cleanmovie.di

import android.content.Context
import com.e444er.cleanmovie.data.remote.TMDBApi
import com.e444er.cleanmovie.data.repository.DataOperationsImpl
import com.e444er.cleanmovie.data.repository.RemoteRepositoryImpl
import com.e444er.cleanmovie.domain.repository.DataStoreOperations
import com.e444er.cleanmovie.domain.repository.RemoteRepository
import com.e444er.cleanmovie.domain.use_case.HomeUseCases
import com.e444er.cleanmovie.domain.use_case.get_movie_genre_list.GetMovieGenreList
import com.e444er.cleanmovie.domain.use_case.get_tv_genre_list.GetTvGenreList
import com.e444er.cleanmovie.domain.use_case.get_locale.GetLocaleUseCase
import com.e444er.cleanmovie.domain.use_case.get_now_playing_movies.GetNowPlayingMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRemoteRepository(tmdbApi: TMDBApi): RemoteRepository {
        return RemoteRepositoryImpl(tmdbApi = tmdbApi)
    }

    @Provides
    @Singleton
    fun provideHomeUseCases(
        remoteRepository: RemoteRepository,
        dataStoreOperations: DataStoreOperations
    ): HomeUseCases {
        return HomeUseCases(
            getMovieGenreList = GetMovieGenreList(remoteRepository),
            getTvGenreList = GetTvGenreList(remoteRepository),
            getNowPlayingMoviesUseCase = GetNowPlayingMoviesUseCase(remoteRepository),
            getLocaleUseCase = GetLocaleUseCase(dataStoreOperations)
        )
    }

    @Provides
    @Singleton
    fun provideDataStoreOperations(
        @ApplicationContext context: Context
    ): DataStoreOperations {
        return DataOperationsImpl(context = context)
    }

}