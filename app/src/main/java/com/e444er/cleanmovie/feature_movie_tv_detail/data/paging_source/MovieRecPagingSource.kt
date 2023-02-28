package com.e444er.cleanmovie.feature_movie_tv_detail.data.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.e444er.cleanmovie.core.domain.models.Movie
import com.e444er.cleanmovie.core.util.Constants.STARTING_PAGE
import com.e444er.cleanmovie.feature_home.data.dto.toMovieList
import com.e444er.cleanmovie.feature_movie_tv_detail.data.remote.DetailApi
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class MovieRecPagingSource @Inject constructor(
    private val detailApi: DetailApi,
    private val language: String,
    private val movieId: Int
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val nextPage = params.key ?: STARTING_PAGE
        return try {
            val response = detailApi.getRecommendationsForMovie(
                movieId = movieId,
                language = language,
                page = nextPage
            )

            LoadResult.Page(
                data = response.results.toMovieList(),
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (nextPage < response.totalPages) response.page.plus(1) else null
            )

        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}