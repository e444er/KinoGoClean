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
        observeNetworkConnectivity()
        setAdaptersClickListener()
        binding.btnNavigateUp.setOnClickListener {
            hideRecyclerViewSeeAll()
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

    private fun slideInLeftAnim(): Animation =
        AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_left)

    private fun showRecyclerViewSeeAll(@StringRes toolBarTextId: Int) {
        val context = requireContext()
        viewModel.setShowsRecyclerViewSeeAllSection(true)
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

    private fun hideRecyclerViewSeeAll() {
        viewModel.setShowsRecyclerViewSeeAllSection(false)
        binding?.let {
            it.recyclerViewSeeAllSection.visibility = View.GONE
            it.scrollView.visibility = View.VISIBLE
            it.recyclerViewSeeAll.removeAllViews()
        }
    }

    private fun setupListenerSeeAllClickEvents() {
        binding?.let {
            it.apply {
                nowPlayingSeeAll.setOnClickListener {
                    setSeeAllPage(R.string.now_playing)
                    recyclerViewSeeAll.adapter = nowPlayingAdapter
                }
                popularMoviesSeeAll.setOnClickListener {
                    setSeeAllPage(R.string.popular_movies)
                    recyclerViewSeeAll.adapter = popularMoviesAdapter
                }
                popularTvSeeAll.setOnClickListener {
                    setSeeAllPage(R.string.popular_tv_series)
                    recyclerViewSeeAll.adapter = popularTvSeriesAdapter
                }
                topRatedMoviesSeeAll.setOnClickListener {
                    setSeeAllPage(R.string.top_rated_movies)
                    recyclerViewSeeAll.adapter = topRatedMoviesAdapter
                }
                topRatedTvSeriesSeeAll.setOnClickListener {
                    setSeeAllPage(R.string.top_rated_tv_series)
                    recyclerViewSeeAll.adapter = topRatedTvSeriesAdapter
                }
            }
        }
    }

    private fun setSeeAllPage(@StringRes toolbarTextId: Int) {
        showRecyclerViewSeeAll(toolbarTextId)
        viewModel.setLatestShowsRecyclerViewSeeAllSection(toolbarTextId)
    }

    private fun observeNetworkConnectivity() {
        var job: Job? = null
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.observeNetworkConnectivity().collectLatest { it ->
                        if (it == ConnectivityObserver.Status.Unavaliable || it == ConnectivityObserver.Status.Lost) {
                            viewModel.showSnackbar()
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

                launch {
                    viewModel.isShowsRecyclerViewSeeAllSection.collectLatest { isShowsSeeAllPage ->
                        if (isShowsSeeAllPage) {
                            viewModel.latestShowsRecyclerViewSeeAllSectionToolBarText.collectLatest { textId ->
                                showRecyclerViewSeeAll(textId)
                                val adapter = when (textId) {
                                    R.string.now_playing -> nowPlayingAdapter
                                    R.string.popular_movies -> popularMoviesAdapter
                                    R.string.popular_tv_series -> popularTvSeriesAdapter
                                    R.string.top_rated_movies -> topRatedMoviesAdapter
                                    R.string.top_rated_tv_series -> topRatedTvSeriesAdapter
                                    else -> nowPlayingAdapter
                                }
                                showRecyclerViewSeeAll(textId)
                                binding?.let {
                                    it.recyclerViewSeeAll.adapter = adapter
                                }
                            }
                        } else {
                            viewModel.setShowsRecyclerViewSeeAllSection(false)
                            hideRecyclerViewSeeAll()
                        }
                    }
                }
            }

        }
    }

    private fun setupRecyclerAdapters() {
        if (binding != null) {
            binding?.apply {
                nowPlayingRecyclerView.adapter = nowPlayingAdapter
                nowPlayingRecyclerView.setAlpha(true)
                popularMoviesRecyclerView.adapter = popularMoviesAdapter
                topRatedMoviesRecyclerView.adapter = topRatedMoviesAdapter
                popularTvSeriesRecyclerView.adapter = popularTvSeriesAdapter
                topRatedTvSeriesRecyclerView.adapter = topRatedTvSeriesAdapter
            }
        }
    }

    private fun collectDataLifecycleAware() =
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
                        topRatedTvSeriesAdapter.passMovieGenreList(genreList)
                    }
                }

//                launch {
//                    val tvGenreList = viewModel.getTvGenreList().genres
//                    if (tvGenreList.isNotEmpty()) {
//                        popularTvSeriesAdapter.passMovieGenreList(tvGenreList)
//                    }
//                }

                launch {
                    viewModel.getTopRatedTvSeries().collectLatest { pagingData ->
                        topRatedTvSeriesAdapter.submitData(pagingData)
                    }
                }
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