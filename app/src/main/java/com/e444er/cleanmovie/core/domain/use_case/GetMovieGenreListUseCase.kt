package com.e444er.cleanmovie.core.domain.use_case

import com.e444er.cleanmovie.core.data.dto.GenreList
import com.e444er.cleanmovie.core.domain.repository.RemoteRepository
import javax.inject.Inject

class GetMovieGenreListUseCase @Inject constructor(
    private val repository: RemoteRepository
) {

    suspend operator fun invoke(language: String): GenreList {
        return repository.getMovieGenreList(language = language.lowercase())
    }
}