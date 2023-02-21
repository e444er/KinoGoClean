package com.e444er.cleanmovie.core.data.repository

import com.e444er.cleanmovie.core.data.data_source.remote.TMDBApi
import com.e444er.cleanmovie.core.data.dto.GenreList
import com.e444er.cleanmovie.core.domain.repository.RemoteRepository
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    private val tmdbApi: TMDBApi
) : RemoteRepository {
    override suspend fun getMovieGenreList(language: String): GenreList {

        return try {
            tmdbApi.getMovieGenreList(language = language)
        } catch (e: Exception) {
            throw e
        }


    }

    override suspend fun getTvGenreList(language: String): GenreList {
        return try {
            tmdbApi.getTvGenreList(language = language)
        } catch (e: Exception) {
            throw e
        }

    }
}