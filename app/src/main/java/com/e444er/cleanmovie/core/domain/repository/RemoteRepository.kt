package com.e444er.cleanmovie.core.domain.repository

import com.e444er.cleanmovie.core.data.dto.GenreList

interface RemoteRepository {

    suspend fun getMovieGenreList(
        language: String
    ): GenreList

    suspend fun getTvGenreList(
        language: String
    ): GenreList
}