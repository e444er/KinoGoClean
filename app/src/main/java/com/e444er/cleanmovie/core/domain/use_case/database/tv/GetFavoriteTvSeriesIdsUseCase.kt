package com.e444er.cleanmovie.core.domain.use_case.database.tv

import com.e444er.cleanmovie.core.domain.repository.LocalDatabaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteTvSeriesIdsUseCase @Inject constructor(
    private val repository: LocalDatabaseRepository
) {
    operator fun invoke(): Flow<List<Int>> {
        return repository.getFavoriteTvSeriesIds()
    }
}