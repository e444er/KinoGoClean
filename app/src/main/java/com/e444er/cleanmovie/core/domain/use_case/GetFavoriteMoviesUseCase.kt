package com.e444er.cleanmovie.core.domain.use_case

import com.e444er.cleanmovie.core.domain.models.FavoriteMovie
import com.e444er.cleanmovie.core.domain.repository.LocalDatabaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteMoviesUseCase @Inject constructor(
    private val repository: LocalDatabaseRepository
) {

    operator fun invoke(): Flow<List<FavoriteMovie>> {
        return repository.getFavoriteMovies()
    }
}