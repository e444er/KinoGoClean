package com.e444er.cleanmovie.feature_explore.presentation.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.e444er.cleanmovie.core.data.dto.Genre
import com.e444er.cleanmovie.core.data.models.enums.Category
import com.e444er.cleanmovie.core.util.Constants.DEFAULT_LANGUAGE
import com.e444er.cleanmovie.feature_explore.data.dto.SearchDto
import com.e444er.cleanmovie.feature_explore.domain.use_case.ExploreUseCases
import com.e444er.cleanmovie.feature_explore.presentation.event.ExploreBottomSheetEvent
import com.e444er.cleanmovie.feature_explore.presentation.event.ExploreFragmentEvent
import com.e444er.cleanmovie.feature_explore.presentation.event.ExploreUiEvent
import com.e444er.cleanmovie.feature_explore.presentation.filter_bottom_sheet.state.FilterBottomState
import com.e444er.cleanmovie.feature_home.domain.models.Movie
import com.e444er.cleanmovie.feature_home.domain.models.TvSeries
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val exploreUseCases: ExploreUseCases,
) : ViewModel() {


    private val _language = MutableStateFlow(DEFAULT_LANGUAGE)
    val language = _language.asStateFlow()

    private val _genreList = MutableStateFlow<List<Genre>>(emptyList())
    val genreList = _genreList.asStateFlow()

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> get() = _query

    private val _filterBottomSheetState = MutableStateFlow(FilterBottomState())
    val filterBottomSheetState = _filterBottomSheetState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<ExploreUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    init {
        viewModelScope.launch {
            exploreUseCases.getLanguageIsoCodeUseCase().collectLatest { language ->
                _language.value = language
                //   getGenreListByCategoriesState(language)
            }
        }

    }

    fun multiSearch(query: String): Flow<PagingData<SearchDto>> {
        return if (query.isNotEmpty()) {
            exploreUseCases.multiSearchUseCase(query = query, language = language.value)
        } else {
            flow { PagingData.empty<SearchDto>() }
        }
    }

    fun discoverMovie(): Flow<PagingData<Movie>> {
        return exploreUseCases.discoverMovieUseCase(
            language = language.value,
            filterBottomState = filterBottomSheetState.value
        )
    }

    fun discoverTv(): Flow<PagingData<TvSeries>> {
        return exploreUseCases.discoverTvUseCase(
            language = language.value,
            filterBottomState = filterBottomSheetState.value
        )
    }


    fun onEventExploreFragment(event: ExploreFragmentEvent) {
        when (event) {
            is ExploreFragmentEvent.MultiSearch -> {
                _query.value = event.query
                if (query.value.isNotEmpty()) {
                    _filterBottomSheetState.update { it.copy(categoryState = Category.SEARCH) }
                } else {
                    _filterBottomSheetState.update { it.copy(categoryState = Category.MOVIE) }
                }
            }
            is ExploreFragmentEvent.RemoveQuery -> {
                _query.value = ""
            }
        }
    }

    fun onEventBottomSheet(event: ExploreBottomSheetEvent) {
        when (event) {
            is ExploreBottomSheetEvent.UpdateCategory -> {
                if (event.checkedCategory == filterBottomSheetState.value.categoryState) {
                    return
                }
                _filterBottomSheetState.update { it.copy(categoryState = event.checkedCategory) }
                //     getGenreListByCategoriesState(language.value)
                resetSelectedGenreIdsState()
            }

            is ExploreBottomSheetEvent.UpdateGenreList -> {
                resetSelectedGenreIdsState()
                _filterBottomSheetState.update { it.copy(checkedGenreIdsState = event.checkedList) }
            }

            is ExploreBottomSheetEvent.UpdateSort -> {
                _filterBottomSheetState.update { it.copy(checkedSortState = event.checkedSort) }
            }

            is ExploreBottomSheetEvent.ResetFilterBottomState -> {
                _filterBottomSheetState.value = FilterBottomState()
            }

            is ExploreBottomSheetEvent.Apply -> {
                viewModelScope.launch { _eventFlow.emit(ExploreUiEvent.PopBackStack) }
            }
        }
    }

    private fun resetSelectedGenreIdsState() {
        _filterBottomSheetState.update { it.copy(checkedGenreIdsState = emptyList()) }
    }

    /*   private fun getGenreListByCategoriesState(language: String) {
           viewModelScope.launch {
               try {
                   _genreList.value =
                       if (_filterBottomSheetState.value.categoryState.isTv()) {
                           exploreUseCases.tvGenreListUseCase(language).genres
                       } else {
                           exploreUseCases.movieGenreListUseCase(language).genres
                       }
               } catch (e: Exception) {
                   _eventFlow.emit(ExploreUiEvent.ShowSnackbar(UiText.StringResource(R.string.internet_error)))
                   Timber.e("Didn't download genreList $e")
               }
           }

       }*/
}