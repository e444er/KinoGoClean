package com.e444er.cleanmovie.data.repository

import com.e444er.cleanmovie.domain.models.Genre
import com.e444er.cleanmovie.domain.repository.RemoteRepository
import javax.inject.Inject

class Repository @Inject constructor(
    private val remoteRepository: RemoteRepository
) {
    suspend fun getMovieGenreList(language: String): List<Genre> {
        return remoteRepository.getMovieGenreList(language = language)
    }

    suspend fun getTvGenreList(language: String): List<Genre> {
        return remoteRepository.getTvGenreList(language = language)
    }
}