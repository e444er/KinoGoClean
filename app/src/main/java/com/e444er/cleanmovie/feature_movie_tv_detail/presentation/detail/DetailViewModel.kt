package com.e444er.cleanmovie.feature_movie_tv_detail.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.e444er.cleanmovie.core.domain.repository.DataStoreOperations
import com.e444er.cleanmovie.core.presentation.util.UiText
import com.e444er.cleanmovie.core.util.Constants.DEFAULT_LANGUAGE
import com.e444er.cleanmovie.core.util.Resource
import com.e444er.cleanmovie.feature_home.domain.models.Movie
import com.e444er.cleanmovie.feature_home.domain.models.TvSeries
import com.e444er.cleanmovie.feature_movie_tv_detail.domain.use_cases.DetailUseCases
import com.e444er.cleanmovie.feature_movie_tv_detail.presentation.detail.event.DetailEvent
import com.e444er.cleanmovie.feature_movie_tv_detail.presentation.detail.event.DetailUiEvent
import com.e444er.cleanmovie.feature_movie_tv_detail.presentation.util.Constants.DETAIL_DEFAULT_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(

    private val detailUseCases: DetailUseCases,
    private val dataStoreOperations: DataStoreOperations,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val _detailState = MutableStateFlow(DetailState())
    val detailState: StateFlow<DetailState> = _detailState.asStateFlow()

    private val languageIsoCode = MutableStateFlow(DEFAULT_LANGUAGE)

    private val _movieIdState = MutableStateFlow(DETAIL_DEFAULT_ID)
    val movieIdState = _movieIdState.asStateFlow()

    private val _tvIdState = MutableStateFlow(DETAIL_DEFAULT_ID)
    val tvIdState = _tvIdState.asStateFlow()

    private val _selectedTabPosition = MutableStateFlow(0)
    val selectedTabPosition = _selectedTabPosition.asStateFlow()

    private val _eventUiFlow = MutableSharedFlow<DetailUiEvent>()
    val eventUiFlow: SharedFlow<DetailUiEvent> = _eventUiFlow.asSharedFlow()

    init {
        getLanguageIsoCode()
    }

    fun getLanguageIsoCode() {
        viewModelScope.launch {
            languageIsoCode.value = dataStoreOperations.getLanguageIsoCode().first()
        }
        savedStateHandle.get<Int>("movieId")?.let { movieId ->
            if (movieId != DETAIL_DEFAULT_ID) {
                _movieIdState.value = movieId
                getMovieDetail(movieId = movieId)
                getMovieVideos(movieId = movieId)
            }
        }
        savedStateHandle.get<Int>("tvId")?.let { tvId ->
            if (tvId != DETAIL_DEFAULT_ID) {
                _tvIdState.value = tvId
                getTvDetail(tvId = tvId)
            }
        }
    }

    private fun getMovieVideos(movieId: Int) {
        viewModelScope.launch {
            updateVideosLoading(isLoading = true)
            val resource = detailUseCases.getMovieVideosUseCase(
                movieId = movieId,
                language = languageIsoCode.value
            )
            when (resource) {
                is Resource.Success -> {
                    updateVideosLoading(isLoading = false)
                    _detailState.update {
                        it.copy(
                            videos = resource.data
                        )
                    }
                }
                is Resource.Error -> {
                    updateVideosLoading(isLoading = false)
                    _eventUiFlow.emit(
                        DetailUiEvent.ShowSnackbar(
                            resource.uiText ?: UiText.unknownError()
                        )
                    )
                }
            }
        }
    }

    private fun updateVideosLoading(isLoading: Boolean) {
        _detailState.update { it.copy(videosLoading = isLoading) }
    }


    fun onEvent(event: DetailEvent) {
        when (event) {
            is DetailEvent.IntentToImdbWebSite -> {
                emitUiEventFlow(DetailUiEvent.IntentToImdbWebSite(addLanguageQueryToTmdbUrl(event.url)))
            }
            is DetailEvent.OnBackPressed -> {
                emitUiEventFlow(DetailUiEvent.PopBackStack)
            }
            is DetailEvent.ClickToDirectorName -> {
                val action =
                    DetailFragmentDirections.actionDetailFragmentToPersonDetailFragment(event.directorId)
                action.isActor = false
                emitUiEventFlow(
                    DetailUiEvent.NavigateTo(action)
                )
            }
            is DetailEvent.ClickActorName -> {
                val action =
                    DetailFragmentDirections.actionDetailFragmentToPersonDetailFragment(event.actorId)
                action.isActor = true
                emitUiEventFlow(
                    DetailUiEvent.NavigateTo(
                        action
                    )
                )
            }
            is DetailEvent.SelectedTab -> {
                _selectedTabPosition.value = event.selectedTabPosition
            }
        }
    }

    private fun emitUiEventFlow(detailUiEvent: DetailUiEvent) {
        viewModelScope.launch {
            _eventUiFlow.emit(detailUiEvent)
        }
    }

    private fun addLanguageQueryToTmdbUrl(tmdbUrl: String): String {
        return tmdbUrl.plus("?language=${languageIsoCode.value}")
    }

    private fun getMovieDetail(movieId: Int) {
        viewModelScope.launch {
            _detailState.value = _detailState.value.copy(loading = true)
            detailUseCases.movieDetailUseCase(
                language = languageIsoCode.value,
                movieId = movieId
            ).collect { resource ->
                when (resource) {
                    is Resource.Error -> {
                        _detailState.update { it.copy(loading = false) }
                        _eventUiFlow.emit(
                            DetailUiEvent.ShowSnackbar(
                                resource.uiText ?: UiText.unknownError()
                            )
                        )
                    }
                    is Resource.Success -> {
                        _detailState.value = DetailState(movieDetail = resource.data)
                    }
                }
            }
        }
    }

    private fun getTvDetail(tvId: Int) {
        viewModelScope.launch {
            _detailState.update { it.copy(loading = true) }
            detailUseCases.tvDetailUseCase(
                language = languageIsoCode.value,
                tvId = tvId
            ).collect { resource ->
                when (resource) {
                    is Resource.Error -> {
                        _detailState.update {
                            it.copy(loading = false)
                        }
                        _eventUiFlow.emit(
                            DetailUiEvent.ShowSnackbar(
                                resource.uiText ?: UiText.unknownError()
                            )
                        )
                    }
                    is Resource.Success -> {
                        _detailState.value = DetailState(tvDetail = resource.data)
                    }
                }
            }
        }
    }

    fun getMovieRecommendations(movieId: Int): Flow<PagingData<Movie>> {
        return detailUseCases.getMovieRecommendationUseCase(
            movieId = movieId,
            language = languageIsoCode.value
        ).cachedIn(viewModelScope)
    }

    fun getTvRecommendations(tvId: Int): Flow<PagingData<TvSeries>> {
        return detailUseCases.getTvRecommendationUseCase(
            tvId = tvId,
            language = languageIsoCode.value
        ).cachedIn(viewModelScope)
    }
}