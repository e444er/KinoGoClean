package com.e444er.cleanmovie.domain.repository

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {

    fun observe(): Flow<Status>

    enum class Status {
        Avaliable,
        Unavaliable,
        Losing,
        Lost
    }
}