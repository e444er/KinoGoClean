package com.e444er.cleanmovie.di

import android.content.Context
import androidx.room.Room
import com.e444er.cleanmovie.core.data.data_source.local.MovaDatabase
import com.e444er.cleanmovie.core.data.repository.LocalDatabaseRepositoryImpl
import com.e444er.cleanmovie.core.domain.repository.LocalDatabaseRepository
import com.e444er.cleanmovie.core.domain.use_case.*
import com.e444er.cleanmovie.core.domain.use_case.database.ClearAllDatabaseUseCase
import com.e444er.cleanmovie.core.domain.use_case.database.LocalDatabaseUseCases
import com.e444er.cleanmovie.core.domain.use_case.database.movie.GetFavoriteMovieIdsUseCase
import com.e444er.cleanmovie.core.domain.use_case.database.movie.GetFavoriteMoviesUseCase
import com.e444er.cleanmovie.core.domain.use_case.database.movie.ToggleMovieForFavoriteListUseCase
import com.e444er.cleanmovie.core.domain.use_case.database.movie.ToggleMovieForWatchListUseCase
import com.e444er.cleanmovie.core.domain.use_case.database.tv.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMovaDatabase(
        @ApplicationContext context: Context
    ): MovaDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = MovaDatabase::class.java,
            "MovaDatabase"
        ).build()
    }

    @Provides
    @Singleton
    fun provideLocalDatabaseRepository(
        database: MovaDatabase
    ): LocalDatabaseRepository {
        return LocalDatabaseRepositoryImpl(database = database)
    }

    @Provides
    @Singleton
    fun provideLocalDatabaseUseCases(
        repository: LocalDatabaseRepository
    ): LocalDatabaseUseCases {
        return LocalDatabaseUseCases(
            clearAllDatabaseUseCase = ClearAllDatabaseUseCase(repository),
            toggleMovieForFavoriteListUseCase = ToggleMovieForFavoriteListUseCase(repository),
            toggleMovieForWatchListUseCase = ToggleMovieForWatchListUseCase(repository),
            getFavoriteMovieIdsUseCase = GetFavoriteMovieIdsUseCase(repository),
            getMovieWatchListItemIdsUseCase = GetMovieWatchListItemIdsUseCase(repository),
            toggleTvSeriesForFavoriteListUseCase = ToggleTvSeriesForFavoriteListUseCase(repository),
            toggleTvSeriesForWatchListItemUseCase = ToggleTvSeriesForWatchListItemUseCase(repository),
            getFavoriteTvSeriesIdsUseCase = GetFavoriteTvSeriesIdsUseCase(repository),
            getTvSeriesWatchListItemIdsUseCase = GetTvSeriesWatchListItemIdsUseCase(repository),
            getFavoriteMoviesUseCase = GetFavoriteMoviesUseCase(repository),
            getFavoriteTvSeriesUseCase = GetFavoriteTvSeriesUseCase(repository),
            getMoviesInWatchListUseCase = GetMoviesInWatchListUseCase(repository),
            getTvSeriesInWatchListUseCase = GetTvSeriesInWatchListUseCase(repository)
        )
    }
}