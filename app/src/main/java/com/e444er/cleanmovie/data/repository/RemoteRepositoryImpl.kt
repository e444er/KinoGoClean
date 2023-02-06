package com.e444er.cleanmovie.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.e444er.cleanmovie.data.paging_source.APIFUNCTIONS
import com.e444er.cleanmovie.data.paging_source.MoviesPagingSource
import com.e444er.cleanmovie.data.paging_source.TvPagingSource
import com.e444er.cleanmovie.data.remote.TMDBApi
import com.e444er.cleanmovie.domain.models.GenreList
import com.e444er.cleanmovie.domain.models.Movie
import com.e444er.cleanmovie.domain.models.TvSeries
import com.e444er.cleanmovie.domain.repository.RemoteRepository
import com.e444er.cleanmovie.util.Constants.ITEMS_PER_PAGE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    private val tmdbApi: TMDBApi
) : RemoteRepository {
    override suspend fun getMovieGenreList(language: String): GenreList {
        return tmdbApi.getMovieGenreList(language = language)
    }

    override suspend fun getTvGenreList(language: String): GenreList {
        return tmdbApi.getTvGenreList(language = language)
    }

    override fun getNowPlayingMovies(
        language: String,
        region: String
    ): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE
            ),
            pagingSourceFactory = {
                MoviesPagingSource(
                    tmdbApi = tmdbApi,
                    language = language,
                    region = region,
                    apiFunc = APIFUNCTIONS.NOWPLAYINGMOVIES
                )
            }
        ).flow
    }

    override fun getPopularMovies(language: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE
            ),
            pagingSourceFactory = {
                MoviesPagingSource(
                    tmdbApi = tmdbApi,
                    language = language,
                    apiFunc = APIFUNCTIONS.POPULARMOVIES
                )
            }
        ).flow
    }

    override fun getTopRatedMovies(language: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE
            ),
            pagingSourceFactory = {
                MoviesPagingSource(
                    tmdbApi = tmdbApi,
                    language = language,
                    apiFunc = APIFUNCTIONS.TOP_RATED_MOVIES
                )
            }
        ).flow
    }

    override fun getPopularTvs(language: String): Flow<PagingData<TvSeries>> {
        return Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE
            ),
            pagingSourceFactory = {
                TvPagingSource(
                    tmdb = tmdbApi,
                    language = language
                )
            }
        ).flow
    }
}