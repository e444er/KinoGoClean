package com.e444er.cleanmovie.domain.use_case.get_popular_tv_series

import androidx.paging.PagingData
import com.e444er.cleanmovie.domain.models.TvSeries
import com.e444er.cleanmovie.domain.repository.RemoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPopularTvSeries @Inject constructor(
    private val remoteRepository: RemoteRepository
) {

    operator fun invoke(language: String): Flow<PagingData<TvSeries>> {
        return remoteRepository.getPopularTvs(
            language = language.lowercase()
        )
    }
}