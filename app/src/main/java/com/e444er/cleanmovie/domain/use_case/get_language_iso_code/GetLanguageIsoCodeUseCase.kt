package com.e444er.cleanmovie.domain.use_case.get_language_iso_code

import com.e444er.cleanmovie.domain.repository.DataStoreOperations
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLanguageIsoCodeUseCase @Inject constructor(
    private val dataStoreOperations: DataStoreOperations
) {

    operator fun invoke(): Flow<String> {
        return dataStoreOperations.getLanguageIsoCode()
    }
}