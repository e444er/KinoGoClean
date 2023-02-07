package com.e444er.cleanmovie.presentation.setting

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.e444er.cleanmovie.R
import com.e444er.cleanmovie.databinding.FragmentHomeBinding
import com.e444er.cleanmovie.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Locale

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SettingsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSettingsBinding.bind(view)
        _binding = binding

        collectDataLifecycleAware()
        setListenerSwitch()

        Locale.getISOLanguages().forEach {
            Timber.d(it)
        }
    }

    private fun setListenerSwitch() {
        binding.switchDarkTheme.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Update uiMode to DarkTheme
                updateUIMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                // Update uiMode to LightTheme
                updateUIMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun updateUIMode(uiMode: Int) {
        viewModel.updateUIMode(uiMode)
        AppCompatDelegate.setDefaultNightMode(uiMode)
    }

    private fun collectDataLifecycleAware() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                viewModel.getUIMode().collectLatest { uiMode ->
                    binding.switchDarkTheme.isChecked = uiMode == AppCompatDelegate.MODE_NIGHT_YES
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}