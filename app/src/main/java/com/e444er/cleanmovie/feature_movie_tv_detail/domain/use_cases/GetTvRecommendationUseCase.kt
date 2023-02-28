package com.e444er.cleanmovie.feature_movie_tv_detail.domain.use_cases

import androidx.paging.PagingData
import androidx.paging.map
import com.e444er.cleanmovie.core.domain.models.TvSeries
import com.e444er.cleanmovie.core.domain.use_case.GetTvGenreListUseCase
import com.e444er.cleanmovie.core.presentation.util.HandleUtils
import com.e444er.cleanmovie.feature_movie_tv_detail.domain.repository.DetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetTvRecommendationUseCase @Inject constructor(
    private val repository: DetailRepository,
    private val getTvGenreListUseCase: GetTvGenreListUseCase
) {
    operator fun invoke(tvId: Int, language: String): Flow<PagingData<TvSeries>> {
        val languageLower = language.lowercase()

        return combine(
            repository.getRecommendationsForTv(tvId = tvId, language = languageLower),
            getTvGenreListUseCase(language = languageLower)
        ) { pagingData, genres ->
            pagingData.map { tv ->
                tv.copy(
                    genreByOne = HandleUtils.handleConvertingGenreListToOneGenreString(
                        genreList = genres,
                        genreIds = tv.genreIds
                    ),
                    voteCountByString = HandleUtils.convertingVoteCountToString(tv.voteCount),
                    firstAirDate = HandleUtils.convertToYearFromDate(
                        releaseDate = tv.firstAirDate ?: ""
                    )
                )
            }
        }
    }
}