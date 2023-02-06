package com.e444er.cleanmovie.presentation.home

import android.content.Context
import android.net.ConnectivityManager
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
import com.e444er.cleanmovie.presentation.home.adapter.PopularMoviesRecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment(
    private val nowPlayingAdapter: NowPlayingRecyclerAdapter,
    private val popularMoviesRecyclerView: PopularMoviesRecyclerView
) : Fragment(R.layout.fragment_home) {

    private var binding: FragmentHomeBinding? = null

    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentHomeBinding.bind(view)

        val connMgr =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (connMgr.activeNetwork != null) {
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch {
                        viewModel.getLanguage().collect {
                            viewModel.setLanguage(it)
                        }
                    }
                    launch {
                        viewModel.getNowPlayingMovies().collectLatest { pagingData ->
                            nowPlayingAdapter.submitData(pagingData)
                        }
                    }
                    launch {
                        viewModel.getNowPlayingMovies().collectLatest { pagingData ->
                            popularMoviesRecyclerView.submitData(pagingData)
                        }
                    }
                    launch {
                        val genreList = viewModel.getMovieGenreList().genres
                        if (genreList.isNotEmpty()) {
                            popularMoviesRecyclerView.passMovieGenreList(genreList)
                            nowPlayingAdapter.passMovieGenreList(genreList)
                        }
                    }
                }
            }
        }


        binding.nowPlayingRecyclerView.adapter = nowPlayingAdapter
        binding.nowPlayingRecyclerView.setAlpha(true)

        binding.popularMoviesRecyclerView.adapter = popularMoviesRecyclerView
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}