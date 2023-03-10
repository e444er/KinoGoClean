package com.e444er.cleanmovie.core.domain.use_case.database

import com.e444er.cleanmovie.core.domain.repository.LocalDatabaseRepository
import javax.inject.Inject

class ClearAllDatabaseUseCase @Inject constructor(
    private val repository: LocalDatabaseRepository
) {
    suspend operator fun invoke() {
        repository.clearDatabase()
    }
}