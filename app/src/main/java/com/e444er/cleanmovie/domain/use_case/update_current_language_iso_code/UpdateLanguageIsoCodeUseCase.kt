package com.e444er.cleanmovie.domain.use_case.update_current_language_iso_code

import com.e444er.cleanmovie.domain.repository.DataStoreOperations
import javax.inject.Inject

class UpdateLanguageIsoCodeUseCase @Inject constructor(
    private val dataStoreOperations: DataStoreOperations
) {

    suspend operator fun invoke(languageTag: String) {
        dataStoreOperations.updateCurrentLanguageIsoCode(languageTag = languageTag)
    }
}