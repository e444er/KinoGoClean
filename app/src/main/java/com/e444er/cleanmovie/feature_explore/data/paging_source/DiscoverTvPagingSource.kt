package com.e444er.cleanmovie.feature_explore.data.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.e444er.cleanmovie.core.domain.models.TvSeries
import com.e444er.cleanmovie.core.presentation.util.toDiscoveryQueryString
import com.e444er.cleanmovie.core.presentation.util.toSeparateWithComma
import com.e444er.cleanmovie.core.util.Constants
import com.e444er.cleanmovie.feature_explore.data.remote.ExploreApi
import com.e444er.cleanmovie.feature_explore.presentation.filter_bottom_sheet.state.FilterBottomState
import com.e444er.cleanmovie.feature_home.data.dto.toTvSeries
import kotlinx.coroutines.withTimeout
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject


class DiscoverTvPagingSource @Inject constructor(
    private val exploreApi: ExploreApi,
    private val filterBottomState: FilterBottomState,
    private val language: String
) : PagingSource<Int, TvSeries>() {
    override fun getRefreshKey(state: PagingState<Int, TvSeries>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvSeries> {

        val timeOutTimeMilli = 15000L


        val nextPage = params.key ?: Constants.STARTING_PAGE
        return try {
            val apiResponse = withTimeout(timeOutTimeMilli) {
                exploreApi.discoverTv(
                    page = nextPage,
                    language = language,
                    genres = filterBottomState.checkedGenreIdsState.toSeparateWithComma(),
                    sort = filterBottomState.checkedSortState.toDiscoveryQueryString(
                        filterBottomState.categoryState
                    ),
                )
            }

            LoadResult.Page(
                data = apiResponse.results.toTvSeries(),
                prevKey = if (nextPage == 1) null else nextPage.minus(1),
                nextKey = if (nextPage < apiResponse.totalPages) nextPage.plus(1) else null
            )

        } catch (e: IOException) {
            LoadResult.Error(throwable = e)
        } catch (e: HttpException) {
            LoadResult.Error(throwable = e)
        }
    }
}