package com.e444er.cleanmovie.feature_home.domain.use_cases

import androidx.paging.PagingData
import androidx.paging.map
import com.e444er.cleanmovie.core.domain.use_case.GetTvGenreListUseCase
import com.e444er.cleanmovie.core.presentation.util.HandleUtils
import com.e444er.cleanmovie.feature_home.domain.models.TvSeries
import com.e444er.cleanmovie.feature_home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetTopRatedTvSeriesUseCase @Inject constructor(
    private val homeRepository: HomeRepository,
    private val getTvGenreListUseCase: GetTvGenreListUseCase
) {
    operator fun invoke(language: String): Flow<PagingData<TvSeries>> {

        val languageLower = language.lowercase()

        return combine(
            homeRepository.getTopRatedTvs(language = languageLower),
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