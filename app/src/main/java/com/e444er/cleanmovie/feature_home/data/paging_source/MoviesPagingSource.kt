package com.e444er.cleanmovie.feature_home.data.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.e444er.cleanmovie.core.data.models.enums.MoviesApiFunction
import com.e444er.cleanmovie.core.domain.models.Movie
import com.e444er.cleanmovie.core.util.Constants.DEFAULT_REGION
import com.e444er.cleanmovie.core.util.Constants.STARTING_PAGE
import com.e444er.cleanmovie.feature_home.data.dto.toMovieList
import com.e444er.cleanmovie.feature_home.data.remote.HomeApi
import javax.inject.Inject

class MoviesPagingSource @Inject constructor(
    private val homeApi: HomeApi,
    private val language: String,
    private val region: String = DEFAULT_REGION,
    private val apiFunc: MoviesApiFunction,
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {

        val nextPage = params.key ?: STARTING_PAGE

        return try {
            val response = when (apiFunc) {
                MoviesApiFunction.NOW_PLAYING_MOVIES -> {
                    homeApi.getNowPlayingMovies(
                        page = nextPage, language = language, region = region
                    )
                }
                MoviesApiFunction.POPULAR_MOVIES -> {
                    homeApi.getPopularMovies(
                        page = nextPage, language = language, region = region
                    )
                }
                MoviesApiFunction.TOP_RATED_MOVIES -> {
                    homeApi.getTopRatedMovies(
                        page = nextPage, language = language, region = region
                    )
                }
            }
            LoadResult.Page(
                data = response.results.toMovieList(),
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (nextPage < response.totalPages) response.page.plus(1) else null
            )

        } catch (e: Exception) {
            LoadResult.Error(throwable = e)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }
}

