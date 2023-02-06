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
import com.e444er.cleanmovie.presentation.home.adapter.PopularMoviesAdapter
import com.e444er.cleanmovie.presentation.home.adapter.PopularTvSeriesAdapter
import com.e444er.cleanmovie.presentation.home.adapter.TopRatedMoviesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment @Inject constructor(
    private val nowPlayingAdapter: NowPlayingRecyclerAdapter,
    private val popularMoviesAdapter: PopularMoviesAdapter,
    private val topRatedMoviesAdapter: TopRatedMoviesAdapter,
    private val popularTvSeriesAdapter: PopularTvSeriesAdapter
) : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentHomeBinding.bind(view)
        _binding = binding

        val connMgr =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (connMgr.activeNetwork != null) {

            viewLifecycleOwner.lifecycleScope.launch {

                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                    launch(Dispatchers.IO) {
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
                        viewModel.getPopularMovies()
                            .collectLatest { pagingData ->
                                popularMoviesAdapter.submitData(pagingData)
                            }
                    }

                    launch{
                        viewModel.getTopRatedMovies()
                            .collectLatest { pagingData ->
                                topRatedMoviesAdapter.submitData(pagingData)
                            }
                    }

                    launch {
                        viewModel.getPopularTvSeries()
                            .collectLatest { pagingData ->
                                popularTvSeriesAdapter.submitData(pagingData)
                            }
                    }

                    launch{
                        val genreList = viewModel.getMovieGenreList().genres
                        if (genreList.isNotEmpty()) {
                            nowPlayingAdapter.passMovieGenreList(genreList)
                            popularMoviesAdapter.passMovieGenreList(genreList)
                            topRatedMoviesAdapter.passMovieGenreList(genreList)
                        }
                    }

                    launch {
                        val tvGenreList = viewModel.getTvGenreList().genres
                        if (tvGenreList.isNotEmpty()) {
                            popularTvSeriesAdapter.passMovieGenreList(tvGenreList)
                        }
                    }
                }
            }
        }
        setupRecyclerAdapters()
        setAdaptersClickListener()
    }


    private fun setupRecyclerAdapters() {
        binding?.apply {
            nowPlayingRecyclerView.adapter = nowPlayingAdapter
            nowPlayingRecyclerView.setAlpha(true)
            popularMoviesRecyclerView.adapter = popularMoviesAdapter
            topRatedMoviesRecyclerView.adapter = topRatedMoviesAdapter
            popularTvSeriesRecyclerView.adapter = popularTvSeriesAdapter
        }
    }

    private fun setAdaptersClickListener() {
        popularMoviesAdapter.setOnItemClickListener {
            Timber.d("Popular Movies ${it.title} ")
        }
        popularTvSeriesAdapter.setOnItemClickListener {
            Timber.d("Popular TvSeries ${it.name} ")
        }
        topRatedMoviesAdapter.setOnItemClickListener {
            Timber.d("TopRated Movies ${it.title} ")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}