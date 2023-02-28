package com.e444er.cleanmovie.core.domain.use_case.database.movie

import com.e444er.cleanmovie.core.domain.models.Movie
import com.e444er.cleanmovie.core.domain.repository.LocalDatabaseRepository
import javax.inject.Inject

class ToggleMovieForWatchListUseCase @Inject constructor(
    private val repository: LocalDatabaseRepository
) {
    suspend operator fun invoke(
        movie: Movie,
        doesAddWatchList: Boolean
    ) {
        if (doesAddWatchList) {
            repository.deleteMovieFromWatchListItem(movieWatchListItem = movie.toMovieWatchListItem())
        } else {
            repository.insertMovieToWatchList(movieWatchListItem = movie.toMovieWatchListItem())
        }
    }
}