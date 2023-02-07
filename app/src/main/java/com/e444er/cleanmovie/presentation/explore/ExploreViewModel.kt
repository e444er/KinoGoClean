package com.e444er.cleanmovie.presentation.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.e444er.cleanmovie.data.models.enums.Categories
import com.e444er.cleanmovie.data.models.enums.Sort
import com.e444er.cleanmovie.domain.models.Genre
import com.e444er.cleanmovie.domain.models.Period
import com.e444er.cleanmovie.domain.use_case.get_locale.GetLocaleUseCase
import com.e444er.cleanmovie.domain.use_case.get_movie_genre_list.GetMovieGenreList
import com.e444er.cleanmovie.domain.use_case.get_tv_genre_list.GetTvGenreList
import com.e444er.cleanmovie.presentation.filter_bottom_sheet.state.FilterBottomState
import com.e444er.cleanmovie.util.Constants.DEFAULT_LANGUAGE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val tvGenreListUseCase: GetTvGenreList,
    private val movieGenreListUseCase: GetMovieGenreList,
    private val getLocaleUseCase: GetLocaleUseCase
) : ViewModel() {

    private val _language = MutableStateFlow<String>(DEFAULT_LANGUAGE)
    val language: StateFlow<String> get() = _language

    private val _genreList = MutableStateFlow<List<Genre>>(emptyList())
    val genreList: StateFlow<List<Genre>> get() = _genreList

    private val _filterBottomSheetState = MutableStateFlow(FilterBottomState())
    val filterBottomSheetState: StateFlow<FilterBottomState> get() = _filterBottomSheetState

    private val _periodState = MutableStateFlow<List<Period>>(emptyList())
    val periodState: StateFlow<List<Period>> get() = _periodState

    private val _isDownloadGenreOptions = MutableSharedFlow<Boolean>()
    val isError: SharedFlow<Boolean> = _isDownloadGenreOptions

    init {
        setupTimePeriods()
        viewModelScope.launch(Dispatchers.IO) {
            getLocale().collectLatest {
                _language.value = it
            }
        }
    }

    fun setCategoryState(newCategory: Categories) {
        if (newCategory == _filterBottomSheetState.value.categoryState) {
            return
        }
        _filterBottomSheetState.update {
            it.copy(
                categoryState = newCategory
            )
        }
        resetSelectedGenreIdsState()
    }

    private fun resetSelectedGenreIdsState() {
        _filterBottomSheetState.update {
            it.copy(
                checkedGenreIdsState = emptyList()
            )
        }
    }

    fun setGenreList(checkedIds: List<Int>) {

        resetSelectedGenreIdsState()

        _filterBottomSheetState.update {
            it.copy(
                checkedGenreIdsState = checkedIds
            )
        }
    }
    fun setCheckedSortState(checkedSort: Sort) {
        _filterBottomSheetState.update {
            it.copy(
                checkedSortState = checkedSort
            )
        }
    }


    fun setCheckedPeriods(checkedPeriodId: Int) {
        _filterBottomSheetState.update {
            it.copy(
                checkedPeriodId = checkedPeriodId
            )
        }
    }

    fun resetFilterBottomState() {
        _filterBottomSheetState.update {
            FilterBottomState()
        }
    }

    private fun getLocale(): Flow<String> {
        return getLocaleUseCase.invoke()
    }

    fun setLocale(locale: String) {
        _language.value = locale
    }

    fun getGenreListByCategoriesState(language: String) {

        viewModelScope.launch {
            try {
                _genreList.value =
                    if (_filterBottomSheetState.value.categoryState == Categories.TV) {
                        tvGenreListUseCase.invoke(language).genres
                    } else {
                        movieGenreListUseCase.invoke(language).genres
                    }
            } catch (e: Exception) {
                _isDownloadGenreOptions.emit(true)
                Timber.e("Didn't download genreList $e")
            }
        }
    }

    private fun setupTimePeriods() {

        val periods = mutableListOf<String>()

        val formatter = SimpleDateFormat("y", Locale.getDefault())
        val calendar = Calendar.getInstance()


        var year = formatter.format(calendar.time).toInt()

        periods.add("All Periods")
        repeat(35) {
            periods.add(year.toString())
            year--
        }

        _periodState.value = periods.mapIndexed { index, s ->
            Period(id = index, time = s)
        }
    }
}


