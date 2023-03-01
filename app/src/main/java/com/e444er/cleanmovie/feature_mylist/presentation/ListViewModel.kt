package com.e444er.cleanmovie.feature_mylist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.e444er.cleanmovie.core.domain.models.Movie
import com.e444er.cleanmovie.core.domain.models.TvSeries
import com.e444er.cleanmovie.core.domain.use_case.database.LocalDatabaseUseCases
import com.e444er.cleanmovie.core.presentation.util.BaseUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val localDatabaseUseCases: LocalDatabaseUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(ListState())
    val state: StateFlow<ListState> = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<BaseUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getMoviesAndTvSeries()
    }


    fun onEvent(event: ListEvent) {
        when (event) {
            is ListEvent.SelectedTab -> {
                _state.update { it.copy(selectedTab = event.listTab) }
                getMoviesAndTvSeries()
            }
            is ListEvent.UpdateListType -> {
                _state.update { it.copy(chipType = event.chipType) }
                getMoviesAndTvSeries()
            }
            is ListEvent.ClickedMovieItem -> {
                navigateToDetailBottomSheet(movie = event.movie)
            }
            is ListEvent.ClickedTvSeriesItem -> {
                navigateToDetailBottomSheet(tvSeries = event.tvSeries)
            }
        }
    }

    private fun navigateToDetailBottomSheet(
        movie: Movie? = null,
        tvSeries: TvSeries? = null
    ) {
        val directions = ListFragmentDirections.actionListFragmentToDetailBottomSheet(
            movie,
            tvSeries
        )
        viewModelScope.launch {
            _eventFlow.emit(
                BaseUiEvent.NavigateTo(directions)
            )
        }
    }

    private fun getMoviesAndTvSeries() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val currentState = state.value
            when (currentState.chipType) {
                ChipType.MOVIE -> {
                    if (currentState.selectedTab.isFavoriteList()) {
                        localDatabaseUseCases.getFavoriteMoviesUseCase()
                            .collectLatest { favoriteMovies ->
                                updateListMovieAndLoading(movieList = favoriteMovies.map { it.movie })
                            }
                    } else {
                        localDatabaseUseCases.getMoviesInWatchListUseCase()
                            .collectLatest { moviesInWathList ->
                                updateListMovieAndLoading(movieList = moviesInWathList.map { it.movie })
                            }
                    }
                }
                ChipType.TVSERIES -> {
                    if (currentState.selectedTab.isFavoriteList()) {
                        localDatabaseUseCases.getFavoriteTvSeriesUseCase()
                            .collectLatest { favoriteTvSeries ->
                                updateListTvSeriesAndLoading(tvSeriesList = favoriteTvSeries.map { it.tvSeries })
                            }
                    } else {
                        localDatabaseUseCases.getTvSeriesInWatchListUseCase()
                            .collectLatest { tvSeriesInWatchList ->
                                updateListTvSeriesAndLoading(tvSeriesList = tvSeriesInWatchList.map { it.tvSeries })
                            }
                    }
                }
            }
        }
    }

    private fun updateListMovieAndLoading(movieList: List<Movie>) {
        _state.update {
            it.copy(
                movieList = movieList,
                isLoading = false
            )
        }
    }

    private fun updateListTvSeriesAndLoading(tvSeriesList: List<TvSeries>) {
        _state.update {
            it.copy(
                tvSeriesList = tvSeriesList,
                isLoading = false
            )
        }
    }
}