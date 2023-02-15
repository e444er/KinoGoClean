package com.e444er.cleanmovie.domain.use_case.get_movie_genre_list

import com.e444er.cleanmovie.data.models.GenreList
import com.e444er.cleanmovie.domain.repository.RemoteRepository
import javax.inject.Inject

class GetMovieGenreList @Inject constructor(
    private val repository: RemoteRepository
) {

    suspend operator fun invoke(language: String): GenreList {
        return repository.getMovieGenreList(language = language)
    }
}