package com.e444er.cleanmovie.feature_mylist.presentation

import com.e444er.cleanmovie.core.domain.models.Movie
import com.e444er.cleanmovie.core.domain.models.TvSeries

data class ListState(
    val selectedTab: ListTab = ListTab.FAVORITELIST,
    val chipType: ChipType = ChipType.MOVIE,
    val isLoading: Boolean = false,
    val movieList: List<Movie> = emptyList(),
    val tvSeriesList: List<TvSeries> = emptyList()
)


enum class ListTab() {
    WATCHLIST,
    FAVORITELIST
}

fun ListTab.isWatchList(): Boolean = this == ListTab.WATCHLIST
fun ListTab.isFavoriteList(): Boolean = this == ListTab.FAVORITELIST

enum class ChipType {
    MOVIE,
    TVSERIES
}
