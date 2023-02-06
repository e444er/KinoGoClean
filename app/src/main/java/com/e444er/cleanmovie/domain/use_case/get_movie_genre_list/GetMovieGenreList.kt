package com.e444er.cleanmovie.domain.use_case.get_movie_genre_list

import com.e444er.cleanmovie.domain.models.Genre
import com.e444er.cleanmovie.domain.repository.RemoteRepository
import javax.inject.Inject

class GetMovieGenreList @Inject constructor(
    private val repository: RemoteRepository
) {

    suspend operator fun invoke(language: String): List<Genre> {
        return repository.getMovieGenreList(language = language)
    }
}