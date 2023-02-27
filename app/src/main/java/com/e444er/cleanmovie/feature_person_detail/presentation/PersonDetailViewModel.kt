package com.e444er.cleanmovie.feature_person_detail.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.e444er.cleanmovie.core.presentation.util.BaseUiEvent
import com.e444er.cleanmovie.core.presentation.util.UiText
import com.e444er.cleanmovie.core.util.Resource
import com.e444er.cleanmovie.feature_person_detail.domain.use_case.PersonDetailUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonDetailViewModel @Inject constructor(
    private val personDetailUseCases: PersonDetailUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(PersonDetailState())
    val state: StateFlow<PersonDetailState> = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<BaseUiEvent>()
    val eventFlow: SharedFlow<BaseUiEvent> = _eventFlow.asSharedFlow()

    private val _language = MutableStateFlow("")
    val language: StateFlow<String> = _language.asStateFlow()

    init {
        getLanguage()
        savedStateHandle.get<Int>("personId")?.let { personId ->
            getPersonDetail(personId = personId)
        }
    }

    private fun getLanguage() {
        viewModelScope.launch {
            val language = personDetailUseCases.getLanguageIsoCodeUseCase().first()
            _language.value = language
        }
    }

    private fun getPersonDetail(personId: Int) {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val result = personDetailUseCases.getPersonDetailUseCase(
                personId = personId,
                language = language.value
            )) {
                is Resource.Success -> {
                    _state.value = PersonDetailState(personDetail = result.data)
                }
                is Resource.Error -> {
                    _state.update { it.copy(isLoading = false) }
                    _eventFlow.emit(
                        BaseUiEvent.ShowSnackbar(
                            result.uiText ?: UiText.unknownError()
                        )
                    )
                }
            }
        }
    }
}