package com.e444er.cleanmovie.presentation.detail_fragment.event

sealed class DetailEvent {
    data class IntentToImdbWebSite(val url: String) : DetailEvent()

    //data class ClickToDirectorName(val directorId: Int) : DetailEvent()
    //data class ClickActorName(val actorId: Int) : DetailEvent()
    data class UpdateMovieId(val movieId: Int) : DetailEvent()
    data class UpdateTvSeriesId(val tvSeriesId: Int) : DetailEvent()
    object OnBackPressed : DetailEvent()
}