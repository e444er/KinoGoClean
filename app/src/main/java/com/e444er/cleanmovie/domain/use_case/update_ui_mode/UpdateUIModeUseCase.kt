package com.e444er.cleanmovie.domain.use_case.update_ui_mode

import com.e444er.cleanmovie.domain.repository.DataStoreOperations
import javax.inject.Inject

class UpdateUIModeUseCase @Inject constructor(
    private val dataStoreOperations: DataStoreOperations
) {
    suspend operator fun invoke(uiMode: Int) {
        dataStoreOperations.updateUIMode(uiMode)
    }
}