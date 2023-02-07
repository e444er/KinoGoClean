package com.e444er.cleanmovie.presentation.home

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.OnBackPressedCallback
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import coil.ImageLoader
import com.e444er.cleanmovie.R
import com.e444er.cleanmovie.databinding.FragmentHomeBinding
import com.e444er.cleanmovie.domain.repository.ConnectivityObserver
import com.e444er.cleanmovie.presentation.home.adapter.*
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    @Inject
    lateinit var imageLoader: ImageLoader

    private val nowPlayingAdapter: NowPlayingRecyclerAdapter by lazy {
        NowPlayingRecyclerAdapter(
            imageLoader
        )
    }
    private val popularMoviesAdapter: PopularMoviesAdapter by lazy {
        PopularMoviesAdapter(
            imageLoader
        )
    }
    private val topRatedMoviesAdapter: TopRatedMoviesAdapter by lazy {
        TopRatedMoviesAdapter(
            imageLoader
        )
    }
    private val popularTvSeriesAdapter: PopularTvSeriesAdapter by lazy {
        PopularTvSeriesAdapter(
            imageLoader
        )
    }
    private val topRatedTvSeriesAdapter: TopRatedTvSeriesAdapter by lazy {
        TopRatedTvSeriesAdapter(
            imageLoader
        )
    }

    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentHomeBinding.bind(view)
        _binding = binding


        addCallback()
        setupListenerSeeAllClickEvents()
        setupRecyclerAdapters()
        setAdaptersClickListener()
        observeNetworkConnectivity()

        binding.btnNavigateUp.setOnClickListener {
            hideRecyclerViewSeeAll()
        }
    }

    private fun hideRecyclerViewSeeAll() {
        binding?.let {
            it.recyclerViewSeeAllSection.visibility = View.GONE
            it.scrollView.visibility = View.VISIBLE
            it.scrollView.animation = slideInRightAnim()
            it.recyclerViewSeeAll.removeAllViews()
        }
    }

    private fun setupListenerSeeAllClickEvents() {
        binding?.let {
            it.apply {
                nowPlayingSeeAll.setOnClickListener {
                    showRecyclerViewSeeAll(R.string.now_playing)
                    recyclerViewSeeAll.adapter = nowPlayingAdapter
                }
                popularMoviesSeeAll.setOnClickListener {
                    showRecyclerViewSeeAll(R.string.popular_movies)
                    recyclerViewSeeAll.adapter = popularMoviesAdapter
                }
                popularTvSeeAll.setOnClickListener {
                    showRecyclerViewSeeAll(R.string.popular_tv_series)
                    recyclerViewSeeAll.adapter = popularTvSeriesAdapter
                }
                topRatedMoviesSeeAll.setOnClickListener {
                    showRecyclerViewSeeAll(R.string.top_rated_movies)
                    recyclerViewSeeAll.adapter = topRatedMoviesAdapter
                }
                topRatedTvSeriesSeeAll.setOnClickListener {
                    showRecyclerViewSeeAll(R.string.top_rated_tv_series)
                    recyclerViewSeeAll.adapter = topRatedTvSeriesAdapter
                }
            }
        }
    }
    private fun slideInLeftAnim(): Animation =
        AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_left)

    private fun slideInRightAnim(): Animation =
        AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_right)

    private fun showRecyclerViewSeeAll(@StringRes toolBarTextId: Int) {
        val context = requireContext()

        binding?.let {
            it.apply {
                scrollView.visibility = View.GONE
                recyclerViewSeeAllSection.visibility = View.VISIBLE
                toolbarText.text = context.getString(toolBarTextId)
                recyclerViewSeeAllSection.animation = slideInLeftAnim()
                recyclerViewSeeAll.layoutManager = GridLayoutManager(requireContext(), 2)
            }
        }
    }

    private fun addCallback() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                hideRecyclerViewSeeAll()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    private fun observeNetworkConnectivity() {
        var job: Job? = null
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.observeNetworkConnectivity().collectLatest {
                        if (it == ConnectivityObserver.Status.Unavaliable || it == ConnectivityObserver.Status.Lost) {
                            viewModel.showSnackBar()
                            job?.cancel()
                        } else if (it == ConnectivityObserver.Status.Avaliable) {
                            job?.cancel()
                            job = collectDataLifecycleAware()
                        }
                    }
                }
                launch {
                    viewModel.showSnackBarNoInternetConnectivity.collectLatest {
                        if (it.isNotEmpty()) {
                            Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun collectDataLifecycleAware() =

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

                launch {
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

                launch {
                    val genreList = viewModel.getMovieGenreList().genres
                    if (genreList.isNotEmpty()) {
                        nowPlayingAdapter.passMovieGenreList(genreList)
                        popularMoviesAdapter.passMovieGenreList(genreList)
                        topRatedMoviesAdapter.passMovieGenreList(genreList)
                    }
                }

//                    launch {
//                        val tvGenreList = viewModel.getTvGenreList().genres
//                        if (tvGenreList.isNotEmpty()) {
//                            popularTvSeriesAdapter.passMovieGenreList(tvGenreList)
//                        }
//                    }

                launch {
                    viewModel.getTopRatedTvSeries().collectLatest { pagingData ->
                        topRatedTvSeriesAdapter.submitData(pagingData)
                    }
                }
            }
        }


    private fun setupRecyclerAdapters() {
        binding?.apply {
            nowPlayingRecyclerView.adapter = nowPlayingAdapter
            nowPlayingRecyclerView.setAlpha(true)
            popularMoviesRecyclerView.adapter = popularMoviesAdapter
            topRatedMoviesRecyclerView.adapter = topRatedMoviesAdapter
            popularTvSeriesRecyclerView.adapter = popularTvSeriesAdapter
            topRatedTvSeriesRecyclerView.adapter = topRatedTvSeriesAdapter
        }
    }

    private fun setAdaptersClickListener() {
        popularMoviesAdapter.setOnItemClickListener { movie ->
            val action = HomeFragmentDirections.actionHomeFragmentToDetailBottomSheetFragment()
            action.movie = movie
            findNavController().navigate(action)
        }

        topRatedMoviesAdapter.setOnItemClickListener { movie ->
            val action = HomeFragmentDirections.actionHomeFragmentToDetailBottomSheetFragment()
            action.movie = movie
            findNavController().navigate(action)
        }

        nowPlayingAdapter.setOnClickListener { movie ->
            val action = HomeFragmentDirections.actionHomeFragmentToDetailBottomSheetFragment()
            action.movie = movie
            findNavController().navigate(action)
        }

        popularTvSeriesAdapter.setOnItemClickListener { tvSeries ->
            val action = HomeFragmentDirections.actionHomeFragmentToDetailBottomSheetFragment()
            action.tvSeries = tvSeries
            findNavController().navigate(action)
        }

        topRatedTvSeriesAdapter.setOnItemClickListener { tvSeries ->
            val action = HomeFragmentDirections.actionHomeFragmentToDetailBottomSheetFragment()
            action.tvSeries = tvSeries
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}