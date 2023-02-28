package com.e444er.cleanmovie.core.domain.use_case

import com.e444er.cleanmovie.core.domain.models.Movie
import com.e444er.cleanmovie.core.domain.repository.LocalDatabaseRepository
import javax.inject.Inject

class ToggleMovieForFavoriteListUseCase @Inject constructor(
    private val repository: LocalDatabaseRepository
) {
    suspend operator fun invoke(
        movie: Movie,
        doesAddFavoriteList: Boolean
    ) {
        if (doesAddFavoriteList) {
            repository.deleteMovieFromFavoriteList(favoriteMovie = movie.toFavoriteMovie())
        } else {
            repository.insertMovieToFavoriteList(favoriteMovie = movie.toFavoriteMovie())
        }
    }
}