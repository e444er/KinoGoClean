package com.e444er.cleanmovie.feature_explore.domain.use_case

import androidx.paging.PagingData
import com.e444er.cleanmovie.feature_explore.data.dto.SearchDto
import com.e444er.cleanmovie.feature_explore.domain.repository.ExploreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MultiSearchUseCase @Inject constructor(
    private val repository: ExploreRepository
) {
    operator fun invoke(
        query: String,
        language: String
    ): Flow<PagingData<SearchDto>> {
        return repository.multiSearch(query = query, language = language)
    }
}