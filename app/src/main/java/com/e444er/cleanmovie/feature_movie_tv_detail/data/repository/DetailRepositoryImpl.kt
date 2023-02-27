package com.e444er.cleanmovie.feature_movie_tv_detail.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.e444er.cleanmovie.core.util.Constants.ITEMS_PER_PAGE
import com.e444er.cleanmovie.feature_home.domain.models.Movie
import com.e444er.cleanmovie.feature_home.domain.models.TvSeries
import com.e444er.cleanmovie.feature_movie_tv_detail.data.remote.DetailApi
import com.e444er.cleanmovie.feature_movie_tv_detail.data.dto.detail.movie.MovieDetailDto
import com.e444er.cleanmovie.feature_movie_tv_detail.data.dto.detail.tv.TvDetailDto
import com.e444er.cleanmovie.feature_movie_tv_detail.data.paging_source.MovieRecPagingSource
import com.e444er.cleanmovie.feature_movie_tv_detail.data.paging_source.TvRecPagingSource
import com.e444er.cleanmovie.feature_movie_tv_detail.domain.repository.DetailRepository
import kotlinx.coroutines.flow.Flow
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

    override fun getRecommendationsForMovie(
        movieId: Int,
        language: String
    ): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            pagingSourceFactory = {
                MovieRecPagingSource(
                    detailApi = detailApi,
                    language = language,
                    movieId = movieId
                )
            }
        ).flow
    }

    override fun getRecommendationsForTv(tvId: Int, language: String): Flow<PagingData<TvSeries>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            pagingSourceFactory = {
                TvRecPagingSource(
                    detailApi = detailApi,
                    language = language,
                    tvId = tvId
                )
            }
        ).flow
    }
}