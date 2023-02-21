package com.e444er.cleanmovie.feature_movie_tv_detail.domain.repository

import com.e444er.cleanmovie.feature_movie_tv_detail.data.dto.detail.movie.MovieDetailDto
import com.e444er.cleanmovie.feature_movie_tv_detail.data.dto.detail.tv.TvDetailDto

interface DetailRepository {
    suspend fun getMovieDetail(
        language: String,
        movieId: Int
    ): MovieDetailDto

    suspend fun getTvDetail(
        language: String,
        tvId: Int
    ): TvDetailDto
}