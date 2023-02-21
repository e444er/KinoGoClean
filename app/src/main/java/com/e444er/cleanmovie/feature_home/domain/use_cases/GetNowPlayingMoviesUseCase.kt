package com.e444er.cleanmovie.feature_home.domain.use_cases

import androidx.paging.PagingData
import com.e444er.cleanmovie.core.util.Constants.DEFAULT_LANGUAGE
import com.e444er.cleanmovie.core.util.Constants.DEFAULT_REGION
import com.e444er.cleanmovie.feature_home.domain.models.Movie
import com.e444er.cleanmovie.feature_home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNowPlayingMoviesUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    operator fun invoke(
        language: String = DEFAULT_LANGUAGE,
        region: String = DEFAULT_REGION
    ): Flow<PagingData<Movie>> {

        return homeRepository.getNowPlayingMovies(
            language = language.lowercase(),
            region = region
        )
    }
}