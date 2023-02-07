package com.e444er.cleanmovie.domain.use_case.discover_tv

import androidx.paging.PagingData
import com.e444er.cleanmovie.domain.models.TvSeries
import com.e444er.cleanmovie.domain.repository.RemoteRepository
import com.e444er.cleanmovie.presentation.filter_bottom_sheet.state.FilterBottomState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DiscoverTvUseCase @Inject constructor(
    private val remoteRepository: RemoteRepository
) {

    operator fun invoke(
        language: String,
        filterBottomState: FilterBottomState
    ): Flow<PagingData<TvSeries>> {
        return remoteRepository.discoverTv(
            language = language,
            filterBottomState = filterBottomState
        )
    }
}