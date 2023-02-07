package com.e444er.cleanmovie.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.e444er.cleanmovie.domain.use_case.update_current_locale.UpdateLocalCurrentUseCase
import com.e444er.cleanmovie.util.Constants.DEFAULT_LANGUAGE
import com.e444er.cleanmovie.util.Constants.supportedLanguages
import com.e444er.cleanmovie.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val updateLocalCurrentUseCase: UpdateLocalCurrentUseCase,
    private val dispatchers: DispatchersProvider
) : ViewModel() {

    private val _isNavigateToHomeFragment = MutableSharedFlow<Boolean>()
    val isNavigateToHomeFragment = _isNavigateToHomeFragment.asSharedFlow()

    fun updateLocale(locale: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (locale !in supportedLanguages) {
                updateLocalCurrentUseCase(locale = DEFAULT_LANGUAGE)
            } else {
                updateLocalCurrentUseCase(locale = locale)
            }
        }
    }

    fun navigateToHomeFragment() {
        viewModelScope.launch(dispatchers.main) {
            delay(1200)
            _isNavigateToHomeFragment.emit(true)
        }
    }
}