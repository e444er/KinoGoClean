package com.e444er.cleanmovie.feature_authentication.presentation.login

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.e444er.cleanmovie.R
import com.e444er.cleanmovie.core.presentation.util.UiEvent
import com.e444er.cleanmovie.core.presentation.util.asString
import com.e444er.cleanmovie.databinding.FragmentLoginBinding
import com.e444er.cleanmovie.feature_authentication.presentation.util.AuthUtil
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentLoginBinding.bind(view)
        _binding = binding

        collectData()

        binding.edtEmail.addTextChangedListener {
            it?.let {
                viewModel.onEvent(LoginEvent.EnteredEmail(it.toString()))
            }
        }

        binding.edtPassword.addTextChangedListener {
            it?.let {
                viewModel.onEvent(LoginEvent.EnteredPassword(it.toString()))
            }
        }

        binding.txtForgotPassword.setOnClickListener {

        }

        binding.btnSignIn.setOnClickListener {
            viewModel.onEvent(LoginEvent.SignIn)
        }

        binding.txtSignUp.setOnClickListener {
            viewModel.onEvent(LoginEvent.ClickedSignUp)
        }
    }

    private fun collectData() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { collectLoginUiEvent() }

                launch { collectEmailState() }

                launch { collectPasswordState() }

                launch { collectLoadingState() }
            }
        }
    }

    private suspend fun collectEmailState() {
        viewModel.emailState.collectLatest { emailState ->
            AuthUtil.updateFieldEmptyErrorInTextInputLayout(
                textInputLayout = binding.layoutEmail,
                authError = emailState.error,
                context = requireContext()
            )
        }
    }

    private suspend fun collectLoadingState() {
        viewModel.isLoading.collectLatest { isLoading ->
            binding.edtEmail.isEnabled = !isLoading
            binding.edtPassword.isEnabled = !isLoading
            binding.btnSignIn.isEnabled = !isLoading
            binding.btnSignInFacebook.isEnabled = !isLoading
            binding.btnSignInGoogle.isEnabled = !isLoading
            binding.progressBar.isVisible = isLoading
        }
    }

    private suspend fun collectPasswordState() {
        viewModel.passwordState.collectLatest { passwordState ->
            AuthUtil.updateFieldEmptyErrorInTextInputLayout(
                textInputLayout = binding.layoutPassword,
                authError = passwordState.error,
                context = requireContext()
            )
        }
    }

    private suspend fun collectLoginUiEvent() {
        viewModel.uiEvent.collectLatest { uiEvent ->
            when (uiEvent) {
                is UiEvent.NavigateTo -> {
                    findNavController().navigate(uiEvent.directions)
                }
                is UiEvent.ShowSnackbar -> {
                    Snackbar.make(
                        requireView(),
                        uiEvent.uiText.asString(
                            requireContext(),
                        ),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}