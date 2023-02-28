package com.e444er.cleanmovie.feature_explore.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.e444er.cleanmovie.core.domain.models.Movie
import com.e444er.cleanmovie.core.domain.models.TvSeries
import com.e444er.cleanmovie.core.util.Constants
import com.e444er.cleanmovie.feature_explore.data.dto.SearchDto
import com.e444er.cleanmovie.feature_explore.data.paging_source.DiscoverMoviePagingSource
import com.e444er.cleanmovie.feature_explore.data.paging_source.DiscoverTvPagingSource
import com.e444er.cleanmovie.feature_explore.data.paging_source.MultiSearchPagingSource
import com.e444er.cleanmovie.feature_explore.data.remote.ExploreApi
import com.e444er.cleanmovie.feature_explore.domain.repository.ExploreRepository
import com.e444er.cleanmovie.feature_explore.presentation.filter_bottom_sheet.state.FilterBottomState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExploreRepositoryImpl @Inject constructor(
    private val exploreApi: ExploreApi
) : ExploreRepository {

    override fun discoverMovie(
        language: String,
        filterBottomState: FilterBottomState
    ): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            pagingSourceFactory = {
                DiscoverMoviePagingSource(
                    exploreApi = exploreApi,
                    filterBottomState = filterBottomState,
                    language = language
                )
            }
        ).flow
    }

    override fun discoverTv(
        language: String,
        filterBottomState: FilterBottomState
    ): Flow<PagingData<TvSeries>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.ITEMS_PER_PAGE
            ),
            pagingSourceFactory = {
                DiscoverTvPagingSource(
                    exploreApi = exploreApi,
                    filterBottomState = filterBottomState,
                    language = language
                )
            }
        ).flow
    }

    override fun multiSearch(query: String, language: String): Flow<PagingData<SearchDto>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.ITEMS_PER_PAGE
            ),
            pagingSourceFactory = {
                MultiSearchPagingSource(
                    exploreApi = exploreApi,
                    query = query,
                    language = language
                )
            }
        ).flow
    }
}