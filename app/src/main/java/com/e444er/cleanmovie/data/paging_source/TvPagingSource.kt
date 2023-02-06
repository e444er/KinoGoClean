package com.e444er.cleanmovie.data.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.e444er.cleanmovie.data.models.toTvSeries
import com.e444er.cleanmovie.data.remote.TMDBApi
import com.e444er.cleanmovie.domain.models.TvSeries
import com.e444er.cleanmovie.util.Constants.STARTING_PAGE
import javax.inject.Inject


class TvPagingSource @Inject constructor(
    private val tmdb: TMDBApi,
    private val language: String
) : PagingSource<Int, TvSeries>() {


    override fun getRefreshKey(state: PagingState<Int, TvSeries>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvSeries> {

        val nextPage = params.key ?: STARTING_PAGE

        return try {
            val response = tmdb.getPopularTvs(
                page = nextPage,
                language = language
            )

            LoadResult.Page(
                data = response.results.toTvSeries(),
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (nextPage < response.totalPages)
                    response.page.plus(1) else null
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}