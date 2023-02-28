package com.e444er.cleanmovie.core.domain.use_case.database.tv

import com.e444er.cleanmovie.core.domain.models.TvSeries
import com.e444er.cleanmovie.core.domain.repository.LocalDatabaseRepository
import javax.inject.Inject

class ToggleTvSeriesForFavoriteListUseCase @Inject constructor(
    private val repository: LocalDatabaseRepository
) {
    suspend operator fun invoke(
        tvSeries: TvSeries,
        doesAddFavorite: Boolean
    ) {
        if (doesAddFavorite) {
            repository.deleteTvSeriesFromFavoriteList(favoriteTvSeries = tvSeries.toFavoriteTvSeries())
        } else {
            repository.insertTvSeriesToFavoriteList(favoriteTvSeries = tvSeries.toFavoriteTvSeries())
        }
    }
}