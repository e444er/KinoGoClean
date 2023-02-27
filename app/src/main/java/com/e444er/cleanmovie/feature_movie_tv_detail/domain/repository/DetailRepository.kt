package com.e444er.cleanmovie.feature_movie_tv_detail.domain.repository

import androidx.paging.PagingData
import com.e444er.cleanmovie.feature_home.domain.models.Movie
import com.e444er.cleanmovie.feature_home.domain.models.TvSeries
import com.e444er.cleanmovie.feature_movie_tv_detail.data.dto.detail.movie.MovieDetailDto
import com.e444er.cleanmovie.feature_movie_tv_detail.data.dto.detail.tv.TvDetailDto
import com.e444er.cleanmovie.feature_movie_tv_detail.data.dto.detail.video.VideosDto
import kotlinx.coroutines.flow.Flow

interface DetailRepository {
    suspend fun getMovieDetail(
        language: String,
        movieId: Int
    ): MovieDetailDto

    suspend fun getTvDetail(
        language: String,
        tvId: Int
    ): TvDetailDto

    fun getRecommendationsForMovie(
        movieId: Int,
        language: String
    ): Flow<PagingData<Movie>>

    fun getRecommendationsForTv(
        tvId: Int,
        language: String
    ): Flow<PagingData<TvSeries>>

    suspend fun getMovieVideos(
        movieId: Int,
        language: String
    ): VideosDto

    suspend fun getTvVideos(
        tvId: Int,
        language: String
    ): VideosDto
}