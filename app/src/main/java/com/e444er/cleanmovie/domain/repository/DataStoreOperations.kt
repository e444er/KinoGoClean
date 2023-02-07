package com.e444er.cleanmovie.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreOperations {

    suspend fun updateCurrentLocale(
        locale: String
    )

    fun getLocale(): Flow<String>

    suspend fun updateUIMode(uiMode: Int)

    fun getUIMode(): Flow<Int>
}