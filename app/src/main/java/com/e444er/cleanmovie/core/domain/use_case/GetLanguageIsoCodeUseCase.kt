package com.e444er.cleanmovie.core.domain.use_case

import com.e444er.cleanmovie.core.domain.repository.DataStoreOperations
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLanguageIsoCodeUseCase @Inject constructor(
    private val dataStoreOperations: DataStoreOperations
) {

    operator fun invoke(): Flow<String> {
        return dataStoreOperations.getLanguageIsoCode()
    }
}