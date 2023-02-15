package com.e444er.cleanmovie.domain.repository

import androidx.paging.PagingData
import com.e444er.cleanmovie.domain.models.*
import com.e444er.cleanmovie.presentation.filter_bottom_sheet.state.FilterBottomState
import com.e444er.cleanmovie.util.Constants.DEFAULT_REGION
import kotlinx.coroutines.flow.Flow
import com.e444er.cleanmovie.data.models.GenreList

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

    fun getTopRatedTvs(
        language: String
    ): Flow<PagingData<TvSeries>>

    fun discoverMovie(
        language: String,
        filterBottomState: FilterBottomState
    ): Flow<PagingData<Movie>>


    fun discoverTv(
        language: String,
        filterBottomState: FilterBottomState
    ): Flow<PagingData<TvSeries>>

    suspend fun getMovieDetail(
        language: String,
        movieId: Int
    ): MovieDetail

    suspend fun getTvDetail(
        language: String,
        tvId: Int
    ): TvDetail
}