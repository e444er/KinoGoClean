package com.e444er.cleanmovie.feature_home.domain.use_cases

import androidx.paging.PagingData
import com.e444er.cleanmovie.feature_home.domain.models.Movie
import com.e444er.cleanmovie.feature_home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    operator fun invoke(
        language: String,
        region: String
    ): Flow<PagingData<Movie>> {
        return homeRepository.getPopularMovies(
            language = language.lowercase(),
            region = region,
        )
    }
}