package com.e444er.cleanmovie.domain.repository

import androidx.paging.PagingData
import com.e444er.cleanmovie.domain.models.GenreList
import com.e444er.cleanmovie.domain.models.Movie
import com.e444er.cleanmovie.domain.models.TvSeries
import com.e444er.cleanmovie.util.Constants.DEFAULT_REGION
import kotlinx.coroutines.flow.Flow

interface RemoteRepository {

    suspend fun getMovieGenreList(
        language: String
    ): GenreList

    suspend fun getTvGenreList(
        language: String
    ): GenreList

    fun getNowPlayingMovies(
        language: String,
        region: String = DEFAULT_REGION
    ): Flow<PagingData<Movie>>

    fun getPopularMovies(
        language: String
    ): Flow<PagingData<Movie>>

    fun getTopRatedMovies(
        language: String
    ): Flow<PagingData<Movie>>

    fun getPopularTvs(
        language: String
    ): Flow<PagingData<TvSeries>>
}