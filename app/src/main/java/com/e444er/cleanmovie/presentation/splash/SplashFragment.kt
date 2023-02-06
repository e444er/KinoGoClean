package com.e444er.cleanmovie.presentation.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.e444er.cleanmovie.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {

    private var coroutine: CoroutineScope? = null

    private var job: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        coroutine = CoroutineScope(context = Dispatchers.Main)

        job = coroutine?.launch {
            delay(1200)
            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        job?.cancel()
        coroutine = null
    }
}