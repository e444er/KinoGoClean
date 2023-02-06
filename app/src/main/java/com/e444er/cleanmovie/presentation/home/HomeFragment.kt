package com.e444er.cleanmovie.presentation.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.e444er.cleanmovie.R
import com.e444er.cleanmovie.databinding.FragmentHomeBinding
import com.e444er.cleanmovie.presentation.home.adapter.NowPlayingRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment(
    private val nowPlayingAdapter: NowPlayingRecyclerAdapter
) : Fragment(R.layout.fragment_home) {

    private var binding: FragmentHomeBinding? = null

    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentHomeBinding.bind(view)

        lifecycleScope.launchWhenCreated {
            viewModel.getLanguage().collectLatest { language ->
                viewModel.getNowPlayingMovies(language = language)
                    .collectLatest {
                        nowPlayingAdapter.submitData(it)
                    }
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.getLanguage().collectLatest {
                val genreList = viewModel.getMovieGenreList(it).genres

                nowPlayingAdapter.passMovieGenreList(genreList)
            }

        }

        binding.nowPlayingRecyclerView.adapter = nowPlayingAdapter
        binding.nowPlayingRecyclerView.setAlpha(true)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}