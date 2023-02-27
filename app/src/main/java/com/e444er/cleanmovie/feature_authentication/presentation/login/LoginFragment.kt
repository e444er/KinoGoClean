package com.e444er.cleanmovie.feature_authentication.presentation.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.e444er.cleanmovie.R
import com.e444er.cleanmovie.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentLoginBinding.bind(view)
        _binding = binding
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}