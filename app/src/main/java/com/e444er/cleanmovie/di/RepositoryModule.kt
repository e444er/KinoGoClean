package com.e444er.cleanmovie.di

import com.e444er.cleanmovie.data.remote.TMDBApi
import com.e444er.cleanmovie.data.repository.RemoteRepositoryImpl
import com.e444er.cleanmovie.data.repository.Repository
import com.e444er.cleanmovie.domain.repository.RemoteRepository
import com.e444er.cleanmovie.domain.use_case.UseCases
import com.e444er.cleanmovie.domain.use_case.get_movie_genre_list.GetMovieGenreList
import com.e444er.cleanmovie.domain.use_case.get_tv_genre_list.GetTvGenreList
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideUseCases(
        repository: Repository
    ): UseCases {
        return UseCases(
            getMovieGenreList = GetMovieGenreList(repository = repository),
            getTvGenreList = GetTvGenreList(repository = repository)
        )
    }
}