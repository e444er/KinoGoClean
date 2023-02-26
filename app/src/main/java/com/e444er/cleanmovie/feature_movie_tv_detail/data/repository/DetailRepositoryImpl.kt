package com.e444er.cleanmovie.feature_movie_tv_detail.data.repository

import com.e444er.cleanmovie.feature_movie_tv_detail.data.remote.DetailApi
import com.e444er.cleanmovie.feature_movie_tv_detail.data.dto.detail.movie.MovieDetailDto
import com.e444er.cleanmovie.feature_movie_tv_detail.data.dto.detail.tv.TvDetailDto
import com.e444er.cleanmovie.feature_movie_tv_detail.domain.repository.DetailRepository
import javax.inject.Inject

class DetailRepositoryImpl @Inject constructor(
    private val detailApi: DetailApi
) : DetailRepository {

    override suspend fun getMovieDetail(language: String, movieId: Int): MovieDetailDto {
        return detailApi.getMovieDetail(
            language = language,
            movieId = movieId
        )
    }

    override suspend fun getTvDetail(language: String, tvId: Int): TvDetailDto {
        return detailApi.getTvDetail(
            language = language,
            tvId = tvId
        )
    }
}