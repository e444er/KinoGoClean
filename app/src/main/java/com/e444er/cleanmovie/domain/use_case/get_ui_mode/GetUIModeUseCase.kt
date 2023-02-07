package com.e444er.cleanmovie.domain.use_case.get_ui_mode

import com.e444er.cleanmovie.domain.repository.DataStoreOperations
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUIModeUseCase @Inject constructor(
    private val dataStoreOperations: DataStoreOperations
) {
    operator fun invoke(): Flow<Int> {
        return dataStoreOperations.getUIMode()
    }
}