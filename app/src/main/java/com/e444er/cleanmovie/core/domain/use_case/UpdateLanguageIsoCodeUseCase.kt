package com.e444er.cleanmovie.core.domain.use_case

import com.e444er.cleanmovie.core.domain.repository.DataStoreOperations
import javax.inject.Inject

class UpdateLanguageIsoCodeUseCase @Inject constructor(
    private val dataStoreOperations: DataStoreOperations
) {

    suspend operator fun invoke(languageTag: String) {
        dataStoreOperations.updateCurrentLanguageIsoCode(languageTag = languageTag.lowercase())
    }
}