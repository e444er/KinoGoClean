package com.e444er.cleanmovie.domain.repository

import androidx.paging.PagingData
import com.e444er.cleanmovie.domain.models.Genre
import com.e444er.cleanmovie.domain.models.Movie
import kotlinx.coroutines.flow.Flow

interface RemoteRepository {

    suspend fun getMovieGenreList(
        language: String
    ): List<Genre>

    suspend fun getTvGenreList(
        language: String
    ): List<Genre>

    suspend fun getNowPlayingMovies(
        language: String,
        region: String
    ): Flow<PagingData<Movie>>
}