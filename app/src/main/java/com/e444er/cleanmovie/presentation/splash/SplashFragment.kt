package com.e444er.cleanmovie.presentation.splash

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.e444er.cleanmovie.R
import com.e444er.cleanmovie.util.getCountryIsoCode
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val viewModel: SplashViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    viewModel.navigateToHomeFragment()
                    viewModel.isNavigateToHomeFragment.collectLatest {
                        if (it) {
                            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
                        }
                    }
                }
                launch {
                    val language = viewModel.getLanguageIsoCode().first()

                    AppCompatDelegate.setApplicationLocales(
                        LocaleListCompat.forLanguageTags(
                            language
                        )
                    )

                }
            }
        }
    }
}
