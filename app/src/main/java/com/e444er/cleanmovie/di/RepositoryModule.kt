package com.e444er.cleanmovie.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.e444er.cleanmovie.core.data.data_source.remote.TMDBApi
import com.e444er.cleanmovie.core.data.repository.DataOperationsImpl
import com.e444er.cleanmovie.core.data.repository.NetworkConnectivityObserver
import com.e444er.cleanmovie.core.domain.repository.ConnectivityObserver
import com.e444er.cleanmovie.core.domain.repository.DataStoreOperations
import com.e444er.cleanmovie.core.domain.repository.RemoteRepository
import com.e444er.cleanmovie.core.data.repository.RemoteRepositoryImpl
import com.e444er.cleanmovie.core.util.DefaultDispatchers
import com.e444er.cleanmovie.core.util.DispatchersProvider
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
    fun provideConnectivityManager(
        @ApplicationContext context: Context
    ): ConnectivityObserver {
        return NetworkConnectivityObserver(context)
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
    fun provideDispatchers(): DispatchersProvider =
        DefaultDispatchers()
}