package com.e444er.cleanmovie.feature_authentication.presentation.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.e444er.cleanmovie.core.presentation.util.StandardTextFieldState
import com.e444er.cleanmovie.feature_authentication.domain.use_case.CreateUserWithEmailAndPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val createUserWithEmailAndPassword: CreateUserWithEmailAndPasswordUseCase
) : ViewModel() {
    private val _emailState = MutableStateFlow(StandardTextFieldState())
    val emailState: StateFlow<StandardTextFieldState> = _emailState.asStateFlow()

    private val _passwordState = MutableStateFlow(StandardTextFieldState())
    val passwordState: StateFlow<StandardTextFieldState> = _passwordState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _uiEvent = MutableSharedFlow<SignUpUiEvent>()
    val uiEvent: SharedFlow<SignUpUiEvent> = _uiEvent.asSharedFlow()

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.EnteredEmail -> {
                _emailState.update { it.copy(text = event.email, error = null) }
            }
            is SignUpEvent.EnteredPassword -> {
                _passwordState.update { it.copy(text = event.password, error = null) }
            }
            is SignUpEvent.SignUp -> {
                createUser(email = emailState.value.text, password = passwordState.value.text)
            }
            is SignUpEvent.SignUpWithGoogle -> {

            }
            is SignUpEvent.SignUpWithFacebook -> {

            }
            is SignUpEvent.ClickedSignIn -> {
                emitUiEvent(SignUpUiEvent.PopBackStack)
            }
        }
    }

    private fun emitUiEvent(uiEvent: SignUpUiEvent) {
        viewModelScope.launch {
            _uiEvent.emit(uiEvent)
        }
    }

    private fun createUser(
        email: String,
        password: String
    ) {
        val result = createUserWithEmailAndPassword(
            email = email,
            password = password,
            onSuccess = {
                emitUiEvent(SignUpUiEvent.NavigateTo(SignUpFragmentDirections.actionSignUpFragmentToHomeFragment()))
                _isLoading.value = false
            },
            onFailure = { uiText ->
                emitUiEvent(SignUpUiEvent.ShowSnackbar(uiText = uiText))
                _isLoading.value = false
            }
        )

        if (result.passwordError == null && result.emailError == null) {
            _isLoading.value = true
        }

        if (result.emailError != null) {
            _emailState.update { it.copy(error = result.emailError) }
        }

        if (result.passwordError != null) {
            _passwordState.update { it.copy(error = result.passwordError) }
        }
    }
}