package com.e444er.cleanmovie.feature_mylist.presentation

import com.e444er.cleanmovie.core.domain.models.Movie
import com.e444er.cleanmovie.core.domain.models.TvSeries

sealed class ListEvent {
    data class SelectedTab(val listTab: ListTab) : ListEvent()
    data class UpdateListType(val listType: ListType) : ListEvent()
    data class ClickedMovieItem(val movie: Movie) : ListEvent()
    data class ClickedTvSeriesItem(val tvSeries: TvSeries) : ListEvent()
}
