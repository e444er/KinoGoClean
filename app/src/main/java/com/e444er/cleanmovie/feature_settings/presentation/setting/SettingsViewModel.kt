package com.e444er.cleanmovie.feature_settings.presentation.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.e444er.cleanmovie.R
import com.e444er.cleanmovie.core.domain.use_case.FirebaseCoreUseCases
import com.e444er.cleanmovie.core.presentation.util.BaseUiEvent
import com.e444er.cleanmovie.core.presentation.util.UiText
import com.e444er.cleanmovie.feature_settings.domain.use_case.SettingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingUseCase: SettingUseCase,
    private val firebaseCoreUseCases: FirebaseCoreUseCases
) : ViewModel() {

    private val _isSignedIn = MutableStateFlow(false)
    val isSignedIn: StateFlow<Boolean> = _isSignedIn.asStateFlow()

    private val _eventFlow = MutableSharedFlow<BaseUiEvent>()
    val eventFlow: SharedFlow<BaseUiEvent> = _eventFlow.asSharedFlow()

    init {
        val isUserSignIn = firebaseCoreUseCases.isUserSignInUseCase()
        _isSignedIn.value = isUserSignIn
    }

    fun getUIMode(): Flow<Int> {
        return settingUseCase.getUIModeUseCase()
    }

    fun updateUIMode(uiMode: Int) {
        viewModelScope.launch {
            settingUseCase.updateUIModeUseCase(uiMode)
        }
    }

    fun updateLanguageIsoCode(languageTag: String) {
        viewModelScope.launch {
            settingUseCase.updateLanguageIsoCodeUseCase(languageTag = languageTag)
        }
    }

    fun logOut() {
        firebaseCoreUseCases.signOutUseCase()
        _isSignedIn.value = false
        viewModelScope.launch {
            _eventFlow.emit(BaseUiEvent.ShowSnackbar(UiText.StringResource(R.string.successfully_log_out)))
        }
    }

    fun getLanguageIsoCode(): Flow<String> {
        return settingUseCase.getLanguageIsoCodeUseCase()
    }

}