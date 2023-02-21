package com.e444er.cleanmovie.feature_explore.domain.use_case

import androidx.paging.PagingData
import com.e444er.cleanmovie.feature_explore.domain.repository.ExploreRepository
import com.e444er.cleanmovie.feature_explore.presentation.filter_bottom_sheet.state.FilterBottomState
import com.e444er.cleanmovie.feature_home.domain.models.TvSeries
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DiscoverTvUseCase @Inject constructor(
    private val exploreRepository: ExploreRepository
) {

    operator fun invoke(
        language: String,
        filterBottomState: FilterBottomState
    ): Flow<PagingData<TvSeries>> {
        return exploreRepository.discoverTv(
            language = language,
            filterBottomState = filterBottomState
        )
    }
}