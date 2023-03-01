package com.e444er.cleanmovie.feature_movie_tv_detail.presentation.detail.event

import com.e444er.cleanmovie.core.domain.models.Movie
import com.e444er.cleanmovie.core.domain.models.TvSeries


sealed class DetailEvent {
    data class IntentToImdbWebSite(val url: String) : DetailEvent()
    data class ClickToDirectorName(val directorId: Int) : DetailEvent()
    data class ClickActorName(val actorId: Int) : DetailEvent()
    object OnBackPressed : DetailEvent()
    data class SelectedTab(val selectedTabPosition: Int) : DetailEvent()
    object ClickedAddWatchList : DetailEvent()
    object ClickedAddFavoriteList : DetailEvent()
    data class ClickRecommendationItemClick(
        val tvSeries: TvSeries? = null,
        val movie: Movie? = null
    ) : DetailEvent()

}