package com.e444er.cleanmovie.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.e444er.cleanmovie.domain.use_case.get_language_iso_code.GetLanguageIsoCodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getLanguageIsoCodeUseCase: GetLanguageIsoCodeUseCase,
) : ViewModel() {

    private val _isNavigateToHomeFragment = MutableSharedFlow<Boolean>()
    val isNavigateToHomeFragment = _isNavigateToHomeFragment.asSharedFlow()

    fun navigateToHomeFragment() {
        viewModelScope.launch() {
            delay(1200)
            _isNavigateToHomeFragment.emit(true)
        }
    }


    fun getLanguageIsoCode(): Flow<String> {
        return getLanguageIsoCodeUseCase()
    }
}