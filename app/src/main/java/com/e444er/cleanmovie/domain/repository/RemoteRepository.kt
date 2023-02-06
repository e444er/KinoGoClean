package com.e444er.cleanmovie.domain.repository

import com.e444er.cleanmovie.domain.models.Genre

interface RemoteRepository {

    suspend fun getMovieGenreList(
        language: String
    ): List<Genre>

    suspend fun getTvGenreList(
        language: String
    ): List<Genre>
}