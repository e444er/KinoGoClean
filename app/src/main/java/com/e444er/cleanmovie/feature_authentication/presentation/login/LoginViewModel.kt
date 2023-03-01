package com.e444er.cleanmovie.feature_authentication.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.e444er.cleanmovie.R
import com.e444er.cleanmovie.core.presentation.util.StandardTextFieldState
import com.e444er.cleanmovie.core.presentation.util.UiEvent
import com.e444er.cleanmovie.core.presentation.util.UiText
import com.e444er.cleanmovie.feature_authentication.domain.use_case.FirebaseUseCases
import com.e444er.cleanmovie.feature_authentication.domain.use_case.SignInWithCredentialUseCase
import com.e444er.cleanmovie.feature_authentication.domain.use_case.SignInWithEmailAndPasswordUseCase
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInWithEmailAndUserName: SignInWithEmailAndPasswordUseCase,
    private val signInWithCredentialUseCase: SignInWithCredentialUseCase,
    private val firebaseUseCases: FirebaseUseCases
) : ViewModel() {

    private val _emailState = MutableStateFlow(StandardTextFieldState())
    val emailState: StateFlow<StandardTextFieldState> = _emailState.asStateFlow()

    private val _passwordState = MutableStateFlow(StandardTextFieldState())
    val passwordState: StateFlow<StandardTextFieldState> = _passwordState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent.asSharedFlow()


    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EnteredEmail -> {
                _emailState.update { it.copy(text = event.email, error = null) }
            }
            is LoginEvent.EnteredPassword -> {
                _passwordState.update { it.copy(text = event.password, error = null) }
            }
            is LoginEvent.ClickedForgetPassword -> {
                val directions =
                    LoginFragmentDirections.actionLoginFragmentToForgetPasswordFragment(null)
                if (emailState.value.text.isNotBlank()) {
                    directions.email = emailState.value.text
                }
                emitUiEvent(UiEvent.NavigateTo(directions))
            }
            is LoginEvent.SignIn -> {
                signIn(
                    email = emailState.value.text,
                    password = passwordState.value.text
                )
            }
            is LoginEvent.SignInWithGoogle -> {
                handleResultsForSignInWithGoogle(task = event.task)
            }
            is LoginEvent.ClickedSignUp -> {
                emitUiEvent(UiEvent.NavigateTo(LoginFragmentDirections.actionLoginFragmentToSignUpFragment()))
            }
            is LoginEvent.OnBackPressed -> {
                emitUiEvent(UiEvent.PopBackStack)
            }
        }
    }

    private fun signIn(email: String, password: String) {
        val loginResult = signInWithEmailAndUserName(
            email = email,
            password = password,
            onSuccess = {
                val movieJob = getMoviesFromFirebaseThenUpdateLocalDatabase()
                val tvSeriesJob = getTvSeriesFromFirebaseThenUpdateLocalDatabase()
                viewModelScope.launch {
                    movieJob.join()
                    tvSeriesJob.join()
                    _uiEvent.emit(UiEvent.NavigateTo(LoginFragmentDirections.actionLoginFragmentToHomeFragment()))
                    _isLoading.value = false
                }
            },
            onFailure = { uiText ->
                emitUiEvent(UiEvent.ShowSnackbar(uiText = uiText))
                _isLoading.value = false
            }
        )

        if (loginResult.passwordError == null && loginResult.emailError == null) {
            _isLoading.value = true
        }

        if (loginResult.emailError != null) {
            _emailState.update { it.copy(error = loginResult.emailError) }
        }
        if (loginResult.passwordError != null) {
            _passwordState.update { it.copy(error = loginResult.passwordError) }
        }
    }

    private fun emitUiEvent(uiEvent: UiEvent) {
        viewModelScope.launch {
            _uiEvent.emit(uiEvent)
        }
    }

    private fun handleResultsForSignInWithGoogle(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful) {
            _isLoading.value = true
            val result = signInWithCredentialUseCase(
                task = task,
                onSuccess = {
                    emitUiEvent(UiEvent.ShowSnackbar(UiText.DynamicText("Successfully login.")))
                    emitUiEvent(UiEvent.NavigateTo(LoginFragmentDirections.actionLoginFragmentToHomeFragment()))
                    _isLoading.value = false
                },
                onFailure = { uiText ->
                    emitUiEvent(UiEvent.ShowSnackbar(uiText))
                    _isLoading.value = false
                }
            )
            if (result.errorMessage != null) {
                emitUiEvent(UiEvent.ShowSnackbar(result.errorMessage))
                _isLoading.value = false
            }
        } else {
            emitUiEvent(UiEvent.ShowSnackbar(UiText.StringResource(R.string.something_went_wrong)))
        }
    }


    private fun getMoviesFromFirebaseThenUpdateLocalDatabase(): Job {
        return viewModelScope.launch(Dispatchers.IO) {
            firebaseUseCases.getFavoriteMovieFromFirebaseThenUpdateLocalDatabaseUseCase(
                onFailure = {},
                coroutineScope = this
            )
            firebaseUseCases.getMovieWatchListFromFirebaseThenUpdateLocalDatabaseUseCase(
                onFailure = {},
                coroutineScope = this
            )
            delay(2000)
        }
    }

    private fun getTvSeriesFromFirebaseThenUpdateLocalDatabase(): Job {
        return viewModelScope.launch(Dispatchers.IO) {
            firebaseUseCases.getFavoriteTvSeriesFromFirebaseThenUpdateLocalDatabaseUseCase(
                onFailure = {},
                coroutineScope = this
            )
            firebaseUseCases.getTvSeriesWatchListFromFirebaseThenUpdateLocalDatabaseUseCase(
                onFailure = {},
                coroutineScope = this
            )
            delay(2000)
        }
    }
}