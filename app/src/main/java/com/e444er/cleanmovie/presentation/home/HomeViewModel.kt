package com.e444er.cleanmovie.presentation.home

import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.e444er.cleanmovie.R
import com.e444er.cleanmovie.data.models.GenreList
import com.e444er.cleanmovie.domain.models.Movie
import com.e444er.cleanmovie.domain.models.TvSeries
import com.e444er.cleanmovie.domain.repository.ConnectivityObserver
import com.e444er.cleanmovie.domain.use_case.HomeUseCases
import com.e444er.cleanmovie.util.Constants.IS_SHOWS_SEE_ALL_PAGE
import com.e444er.cleanmovie.util.Constants.LATEST_SHOWS_SEE_ALL_PAGE_TOOLBAR_TEXT_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCases: HomeUseCases,
    private val networkConnectivityObserver: ConnectivityObserver,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _languageIsoCode = MutableStateFlow("")
    val languageIsoCode: StateFlow<String> get() = _languageIsoCode

    val isShowsRecyclerViewSeeAllSection =
        savedStateHandle.getStateFlow(IS_SHOWS_SEE_ALL_PAGE, false)

    val latestShowsRecyclerViewSeeAllSectionToolBarText = savedStateHandle.getStateFlow(
        LATEST_SHOWS_SEE_ALL_PAGE_TOOLBAR_TEXT_ID,
        R.string.now_playing
    )

    fun observeNetworkConnectivity() = networkConnectivityObserver.observe()

    fun setShowsRecyclerViewSeeAllSection(value: Boolean) {
        savedStateHandle[IS_SHOWS_SEE_ALL_PAGE] = value
    }

    fun setLatestShowsRecyclerViewSeeAllSection(@StringRes toolbarTextId: Int) {
        savedStateHandle[LATEST_SHOWS_SEE_ALL_PAGE_TOOLBAR_TEXT_ID] = toolbarTextId
    }


    fun getLanguageIsoCode(): Flow<String> {
        return homeUseCases.getLanguageIsoCodeUseCase()
    }

    fun setLanguageIsoCode(languageIsoCode: String) {
        _languageIsoCode.value = languageIsoCode
        setLanguageIsoCodeInDataStore(languageIsoCode)
    }

    private fun setLanguageIsoCodeInDataStore(languageIsoCode: String) {
        viewModelScope.launch {
            homeUseCases.updateLanguageIsoCodeUseCase(languageIsoCode)
        }
    }

    suspend fun getMovieGenreList(): GenreList {
        return homeUseCases.getMovieGenreList(_languageIsoCode.value.lowercase())
    }

    suspend fun getTvGenreList(): GenreList {
        return homeUseCases.getTvGenreList(_languageIsoCode.value.lowercase())
    }

    fun getNowPlayingMovies(): Flow<PagingData<Movie>> {
        return homeUseCases.getNowPlayingMoviesUseCase(
            language = _languageIsoCode.value.lowercase()
        ).cachedIn(viewModelScope)
    }

    fun getPopularMovies(): Flow<PagingData<Movie>> {
        return homeUseCases.getPopularMoviesUseCase(
            language = _languageIsoCode.value.lowercase()
        ).cachedIn(viewModelScope)
    }

    fun getTopRatedMovies(): Flow<PagingData<Movie>> {
        return homeUseCases.getTopRatedMoviesUseCase(
            language = _languageIsoCode.value.lowercase()
        ).cachedIn(viewModelScope)
    }

    fun getPopularTvSeries(): Flow<PagingData<TvSeries>> {
        return homeUseCases.getPopularTvSeries(
            language = _languageIsoCode.value.lowercase()
        )
    }

    fun getTopRatedTvSeries(): Flow<PagingData<TvSeries>> {
        return homeUseCases.getTopRatedTvSeriesUseCase(
            language = _languageIsoCode.value.lowercase()
        )
    }
}