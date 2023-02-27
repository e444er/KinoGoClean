package com.e444er.cleanmovie.feature_movie_tv_detail.presentation.detail.event

import com.e444er.cleanmovie.feature_home.domain.models.Movie
import com.e444er.cleanmovie.feature_home.domain.models.TvSeries

sealed class DetailEvent {
    data class IntentToImdbWebSite(val url: String) : DetailEvent()
    data class ClickToDirectorName(val directorId: Int) : DetailEvent()
    data class ClickActorName(val actorId: Int) : DetailEvent()
    object OnBackPressed : DetailEvent()
    data class SelectedTab(val selectedTabPosition: Int) : DetailEvent()
    data class ClickRecommendationItemClick(
        val tvSeries: TvSeries? = null,
        val movie: Movie? = null
    ) : DetailEvent()

}