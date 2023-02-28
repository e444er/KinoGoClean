package com.e444er.cleanmovie.feature_home.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.e444er.cleanmovie.core.data.models.enums.MoviesApiFunction
import com.e444er.cleanmovie.core.data.models.enums.TvSeriesApiFunction
import com.e444er.cleanmovie.core.domain.models.Movie
import com.e444er.cleanmovie.core.domain.models.TvSeries
import com.e444er.cleanmovie.core.util.Constants
import com.e444er.cleanmovie.feature_home.data.paging_source.TvPagingSource
import com.e444er.cleanmovie.feature_home.data.remote.HomeApi
import com.e444er.cleanmovie.feature_home.domain.repository.HomeRepository
import com.e444er.cleanmovie.feature_home.data.paging_source.MoviesPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeApi: HomeApi
) : HomeRepository {

    override fun getNowPlayingMovies(
        language: String,
        region: String
    ): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.ITEMS_PER_PAGE
            ),
            pagingSourceFactory = {
                MoviesPagingSource(
                    homeApi = homeApi,
                    language = language,
                    region = region,
                    apiFunc = MoviesApiFunction.NOW_PLAYING_MOVIES
                )
            }
        ).flow
    }

    override fun getPopularMovies(language: String, region: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.ITEMS_PER_PAGE
            ),
            pagingSourceFactory = {
                MoviesPagingSource(
                    homeApi = homeApi,
                    language = language,
                    apiFunc = MoviesApiFunction.POPULAR_MOVIES
                )
            }
        ).flow
    }

    override fun getTopRatedMovies(language: String, region: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.ITEMS_PER_PAGE
            ),
            pagingSourceFactory = {
                MoviesPagingSource(
                    homeApi = homeApi,
                    language = language,
                    apiFunc = MoviesApiFunction.TOP_RATED_MOVIES
                )
            }
        ).flow
    }

    override fun getPopularTvs(language: String): Flow<PagingData<TvSeries>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.ITEMS_PER_PAGE
            ),
            pagingSourceFactory = {
                TvPagingSource(
                    homeApi = homeApi,
                    language = language,
                    apiFunction = TvSeriesApiFunction.POPULAR_TV
                )
            }
        ).flow
    }

    override fun getTopRatedTvs(language: String): Flow<PagingData<TvSeries>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.ITEMS_PER_PAGE
            ),
            pagingSourceFactory = {
                TvPagingSource(
                    homeApi = homeApi,
                    language = language,
                    apiFunction = TvSeriesApiFunction.TOP_RATED_TV
                )
            }
        ).flow
    }
}