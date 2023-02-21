package com.e444er.cleanmovie.feature_home.domain.use_cases

import androidx.paging.PagingData
import com.e444er.cleanmovie.feature_home.domain.models.TvSeries
import com.e444er.cleanmovie.feature_home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPopularTvSeriesUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {

    operator fun invoke(language: String): Flow<PagingData<TvSeries>> {
        return homeRepository.getPopularTvs(
            language = language.lowercase()
        )
    }
}