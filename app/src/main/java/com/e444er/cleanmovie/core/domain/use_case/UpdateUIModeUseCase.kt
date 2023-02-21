package com.e444er.cleanmovie.core.domain.use_case

import com.e444er.cleanmovie.core.domain.repository.DataStoreOperations
import javax.inject.Inject

class UpdateUIModeUseCase @Inject constructor(
    private val dataStoreOperations: DataStoreOperations
) {
    suspend operator fun invoke(uiMode: Int) {
        dataStoreOperations.updateUIMode(uiMode)
    }
}