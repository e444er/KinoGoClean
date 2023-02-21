package com.e444er.cleanmovie.feature_explore.domain.repository

import androidx.paging.PagingData
import com.e444er.cleanmovie.feature_explore.presentation.filter_bottom_sheet.state.FilterBottomState
import com.e444er.cleanmovie.feature_home.domain.models.Movie
import com.e444er.cleanmovie.feature_home.domain.models.TvSeries
import kotlinx.coroutines.flow.Flow

interface ExploreRepository {

    fun discoverMovie(
        language: String,
        filterBottomState: FilterBottomState
    ): Flow<PagingData<Movie>>


    fun discoverTv(
        language: String,
        filterBottomState: FilterBottomState
    ): Flow<PagingData<TvSeries>>

}