package com.e444er.cleanmovie.data.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.e444er.cleanmovie.data.models.toMovieList
import com.e444er.cleanmovie.data.remote.TMDBApi
import com.e444er.cleanmovie.domain.models.Movie
import com.e444er.cleanmovie.util.Constants.STARTING_PAGE
import javax.inject.Inject

class NowPlayingPagingSource @Inject constructor(
    private val tmdbApi: TMDBApi,
    private val language: String,
    private val region: String
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {

            val response = tmdbApi.getNowPlayingMovies(
                language = language,
                region = region
            )

            val page = response.page

            LoadResult.Page(
                data = response.results.toMovieList(),
                prevKey = if (page != STARTING_PAGE) page - 1 else STARTING_PAGE,
                nextKey = page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(throwable = e)
        }
    }
}