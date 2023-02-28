package com.e444er.cleanmovie.core.domain.use_case.database.tv

import com.e444er.cleanmovie.core.domain.models.TvSeries
import com.e444er.cleanmovie.core.domain.repository.LocalDatabaseRepository
import javax.inject.Inject

class ToggleTvSeriesForWatchListItemUseCase @Inject constructor(
    private val repository: LocalDatabaseRepository
) {
    suspend operator fun invoke(
        tvSeries: TvSeries,
        doesAddWatchList: Boolean
    ) {
        if (doesAddWatchList) {
            repository.deleteTvSeriesFromWatchListItem(tvSeriesWatchListItem = tvSeries.toTvSeriesWatchListItem())
        } else {
            repository.insertTvSeriesToWatchListItem(tvSeriesWatchListItem = tvSeries.toTvSeriesWatchListItem())
        }
    }
}