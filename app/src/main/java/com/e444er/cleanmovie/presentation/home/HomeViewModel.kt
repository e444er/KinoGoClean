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
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCases: HomeUseCases
) : ViewModel() {

    suspend fun getMovieGenreList(language: String): GenreList {
        return homeUseCases.getMovieGenreList(language)
    }

    fun getNowPlayingMovies(language: String): Flow<PagingData<Movie>> {
        return homeUseCases.getNowPlayingMoviesUseCase(
            language = language.lowercase()
        ).cachedIn(viewModelScope)
    }

    fun getLanguage(): Flow<String> {
        return homeUseCases.getLocaleUseCase()
    }
}