package com.e444er.cleanmovie.feature_explore.domain.use_case

import androidx.paging.PagingData
import androidx.paging.map
import com.e444er.cleanmovie.core.domain.use_case.GetMovieGenreListUseCase
import com.e444er.cleanmovie.core.presentation.util.HandleUtils
import com.e444er.cleanmovie.feature_explore.domain.repository.ExploreRepository
import com.e444er.cleanmovie.feature_explore.presentation.filter_bottom_sheet.state.FilterBottomState
import com.e444er.cleanmovie.feature_home.domain.models.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.combineTransform
import javax.inject.Inject

class DiscoverMovieUseCase @Inject constructor(
    private val exploreRepository: ExploreRepository,
    private val getMovieGenreListUseCase: GetMovieGenreListUseCase
) {

    operator fun invoke(
        language: String,
        filterBottomState: FilterBottomState
    ): Flow<PagingData<Movie>> {

        return combine(
            exploreRepository.discoverMovie(language, filterBottomState),
            getMovieGenreListUseCase(language)
        ) { pagingData, movieGenreList ->
            pagingData.map { movie ->
                movie.copy(
                    genreByOne = HandleUtils.handleConvertingGenreListToOneGenreString(
                        genreList = movieGenreList,
                        genreIds = movie.genreIds
                    ),
                    releaseDate = HandleUtils.convertToYearFromDate(movie.releaseDate ?: ""),
                    voteCountByString = HandleUtils.convertingVoteCountToString(movie.voteCount)
                )
            }
        }
    }
}