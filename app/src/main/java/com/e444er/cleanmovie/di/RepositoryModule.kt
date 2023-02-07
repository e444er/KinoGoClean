package com.e444er.cleanmovie.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.e444er.cleanmovie.data.remote.TMDBApi
import com.e444er.cleanmovie.data.repository.DataOperationsImpl
import com.e444er.cleanmovie.data.repository.NetworkConnectivityObserver
import com.e444er.cleanmovie.data.repository.RemoteRepositoryImpl
import com.e444er.cleanmovie.domain.repository.ConnectivityObserver
import com.e444er.cleanmovie.domain.repository.DataStoreOperations
import com.e444er.cleanmovie.domain.repository.RemoteRepository
import com.e444er.cleanmovie.domain.use_case.ExploreUseCases
import com.e444er.cleanmovie.domain.use_case.HomeUseCases
import com.e444er.cleanmovie.domain.use_case.discover_movie.DiscoverMovieUseCase
import com.e444er.cleanmovie.domain.use_case.discover_tv.DiscoverTvUseCase
import com.e444er.cleanmovie.domain.use_case.get_movie_genre_list.GetMovieGenreList
import com.e444er.cleanmovie.domain.use_case.get_tv_genre_list.GetTvGenreList
import com.e444er.cleanmovie.domain.use_case.get_locale.GetLocaleUseCase
import com.e444er.cleanmovie.domain.use_case.get_now_playing_movies.GetNowPlayingMoviesUseCase
import com.e444er.cleanmovie.domain.use_case.get_popular_movies.GetPopularMoviesUseCase
import com.e444er.cleanmovie.domain.use_case.get_popular_tv_series.GetPopularTvSeries
import com.e444er.cleanmovie.domain.use_case.get_top_rated_movies.GetTopRatedMoviesUseCase
import com.e444er.cleanmovie.domain.use_case.get_top_rated_tv_series.GetTopRatedTvSeriesUseCase
import com.e444er.cleanmovie.util.DefaultDispatchers
import com.e444er.cleanmovie.util.DispatchersProvider
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
    fun provideExploreUseCases(
        remoteRepository: RemoteRepository,
        dataStoreOperations: DataStoreOperations
    ): ExploreUseCases {
        return ExploreUseCases(
            tvGenreListUseCase = GetTvGenreList(remoteRepository),
            movieGenreListUseCase = GetMovieGenreList(remoteRepository),
            getLocaleUseCase = GetLocaleUseCase(dataStoreOperations),
            discoverMovieUseCase = DiscoverMovieUseCase(remoteRepository),
            discoverTvUseCase = DiscoverTvUseCase(remoteRepository)
        )
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
            getLocaleUseCase = GetLocaleUseCase(dataStoreOperations),
            getPopularMoviesUseCase = GetPopularMoviesUseCase(remoteRepository),
            getTopRatedMoviesUseCase = GetTopRatedMoviesUseCase(remoteRepository),
            getPopularTvSeries = GetPopularTvSeries(remoteRepository),
            getTopRatedTvSeriesUseCase = GetTopRatedTvSeriesUseCase(remoteRepository)
        )
    }

    @Provides
    @Singleton
    fun provideDataStoreOperations(
        dataStore: DataStore<Preferences>
    ): DataStoreOperations {
        return DataOperationsImpl(dataStore = dataStore)
    }

    @Provides
    @Singleton
    fun provideConnectivityManager(
        @ApplicationContext context: Context
    ): ConnectivityObserver {
        return NetworkConnectivityObserver(context)
    }

    @Provides
    @Singleton
    fun provideDispatchers(): DispatchersProvider =
        DefaultDispatchers()
}