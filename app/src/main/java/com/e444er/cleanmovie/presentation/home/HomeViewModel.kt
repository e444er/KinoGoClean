package com.e444er.cleanmovie.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.e444er.cleanmovie.domain.models.GenreList
import com.e444er.cleanmovie.domain.models.Movie
import com.e444er.cleanmovie.domain.use_case.HomeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCases: HomeUseCases
) : ViewModel() {

    private val _language = MutableStateFlow("")
    val language: StateFlow<String> get() = _language


    suspend fun getMovieGenreList(): GenreList {
        return homeUseCases.getMovieGenreList(_language.value)
    }

    fun getNowPlayingMovies(): Flow<PagingData<Movie>> {
        return homeUseCases.getNowPlayingMoviesUseCase(
            language = _language.value
        ).cachedIn(viewModelScope)
    }

    fun getPopularMovies(): Flow<PagingData<Movie>> {
        return homeUseCases.getPopularMoviesUseCase(
            language = _language.value
        ).cachedIn(viewModelScope)
    }


    fun getLanguage(): Flow<String> {
        return homeUseCases.getLocaleUseCase()
    }

    fun setLanguage(language: String) {
        _language.value = language
    }
}