package com.e444er.cleanmovie.feature_authentication.presentation.sign_up

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.e444er.cleanmovie.R
import com.e444er.cleanmovie.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private var _binding: FragmentSignUpBinding? = null
    val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSignUpBinding.bind(view)
        _binding = binding
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}