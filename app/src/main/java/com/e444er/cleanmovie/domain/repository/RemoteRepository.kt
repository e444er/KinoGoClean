package com.e444er.cleanmovie.domain.repository

import androidx.paging.PagingData
import com.e444er.cleanmovie.domain.models.Genre
import com.e444er.cleanmovie.domain.models.GenreList
import com.e444er.cleanmovie.domain.models.Movie
import com.e444er.cleanmovie.util.Constants
import kotlinx.coroutines.flow.Flow

interface RemoteRepository {

    suspend fun getMovieGenreList(
        language: String
    ): GenreList

    suspend fun getTvGenreList(
        language: String
    ): GenreList

    fun getNowPlayingMovies(
        language: String = Constants.DEFAULT_LANGUAGE,
        region: String = Constants.DEFAULT_REGION
    ): Flow<PagingData<Movie>>

    fun getPopularMovies(
        language: String = Constants.DEFAULT_LANGUAGE
    ): Flow<PagingData<Movie>>
}