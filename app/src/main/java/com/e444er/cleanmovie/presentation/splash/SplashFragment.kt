package com.e444er.cleanmovie.presentation.splash

import android.os.Bundle
import android.view.View
import androidx.core.os.ConfigurationCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.e444er.cleanmovie.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val viewModel: SplashViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val locale =
            ConfigurationCompat.getLocales(requireContext().resources.configuration)[0]?.country.toString()
        Timber.d(locale)

        viewModel.updateLocale(locale = locale)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.navigateToHomeFragment()
                viewModel.isNavigateToHomeFragment.collectLatest {
                    if (it) {
                        findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
                    }
                }
            }
        }
    }
}