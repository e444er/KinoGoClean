package com.e444er.cleanmovie.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.e444er.cleanmovie.data.paging_source.NowPlayingPagingSource
import com.e444er.cleanmovie.data.remote.TMDBApi
import com.e444er.cleanmovie.domain.models.Genre
import com.e444er.cleanmovie.domain.models.GenreList
import com.e444er.cleanmovie.domain.models.Movie
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
                NowPlayingPagingSource(
                    tmdbApi = tmdbApi,
                    language = language,
                    region = region
                )
            }
        ).flow
    }
}