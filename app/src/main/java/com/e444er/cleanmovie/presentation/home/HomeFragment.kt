package com.e444er.cleanmovie.presentation.home

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.OnBackPressedCallback
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.e444er.cleanmovie.R
import com.e444er.cleanmovie.data.models.Genre
import com.e444er.cleanmovie.databinding.FragmentHomeBinding
import com.e444er.cleanmovie.domain.repository.ConnectivityObserver
import com.e444er.cleanmovie.presentation.home.adapter.*
import com.e444er.cleanmovie.presentation.util.UiEvent
import com.e444er.cleanmovie.presentation.util.UiText
import com.e444er.cleanmovie.presentation.util.asString
import com.e444er.cleanmovie.util.getCountryIsoCode
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {


    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val handler = CoroutineExceptionHandler { _, throwable ->
        Timber.e("Caught Exception $throwable")
    }

    @Inject
    lateinit var nowPlayingAdapter: NowPlayingRecyclerAdapter

    @Inject
    lateinit var popularMoviesAdapter: PopularMoviesAdapter

    @Inject
    lateinit var popularTvSeriesAdapter: PopularTvSeriesAdapter

    @Inject
    lateinit var topRatedMoviesAdapter: TopRatedMoviesAdapter

    @Inject
    lateinit var topRatedTvSeriesAdapter: TopRatedTvSeriesAdapter

    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentHomeBinding.bind(view)
        _binding = binding
        updateCountryIsoCode()
        collectDataLifecycleAware()
        addCallback()
        setupListenerSeeAllClickEvents()
        setupRecyclerAdapters()
        setAdaptersClickListener()
        binding.btnNavigateUp.setOnClickListener {
            viewModel.onEvent(HomeEvent.NavigateUpFromSeeAllSection)
        }
    }

    private fun updateCountryIsoCode() {
        val countryIsoCode = requireContext().getCountryIsoCode().uppercase()
        viewModel.onEvent(HomeEvent.UpdateCountryIsoCode(countryIsoCode))
    }

    private fun retryAllPagingAdapter() {
        nowPlayingAdapter.retry()
        popularMoviesAdapter.retry()
        popularTvSeriesAdapter.retry()
        topRatedMoviesAdapter.retry()
        topRatedTvSeriesAdapter.retry()
    }

    private fun collectDataLifecycleAware() =
        viewLifecycleOwner.lifecycleScope.launch(handler) {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                supervisorScope {
                    launch {
                        viewModel.homeState.collectLatest { homeState ->
                            passTvGenreListToRecyclerAdapter(homeState.tvGenreList)
                            passMovieGenreListToRecyclerAdapter(homeState.movieGenreList)
                            binding.apply {
                                seeAllPage.isVisible = homeState.isShowsSeeAllPage
                                scrollView.isVisible = !homeState.isShowsSeeAllPage
                                if (homeState.isShowsSeeAllPage) {
                                    showSeeAllPage(homeState.seeAllPageToolBarText)
                                } else {
                                    hideSeeAllPage()
                                }
                            }
                        }
                    }

                    launch {
                        observeNetworkConnectivity()
                    }

                    launch {
                        viewModel.eventFlow.collect { uiEvent ->
                            when (uiEvent) {
                                is UiEvent.NavigateTo -> findNavController().navigate(uiEvent.directions)
                            }
                        }
                    }

                    launch {
                        viewModel.getNowPlayingMovies().collectLatest { pagingData ->
                            nowPlayingAdapter.submitData(pagingData)
                        }
                    }

                    launch {
                        viewModel.getPopularMovies().collectLatest { pagingData ->
                            popularMoviesAdapter.submitData(pagingData)
                        }
                    }

                    launch {
                        viewModel.getTopRatedMovies().collectLatest { pagingData ->
                            topRatedMoviesAdapter.submitData(pagingData)
                        }
                    }

                    launch {
                        viewModel.getPopularTvSeries().collectLatest { pagingData ->
                            popularTvSeriesAdapter.submitData(pagingData)
                        }
                    }

                    launch {
                        viewModel.getTopRatedTvSeries().collectLatest { pagingData ->
                            topRatedTvSeriesAdapter.submitData(pagingData)
                        }
                    }
                }
            }
        }

    private suspend fun observeNetworkConnectivity() {
        viewModel.observeNetworkConnectivity().collectLatest {
            if (it == ConnectivityObserver.Status.Unavaliable || it == ConnectivityObserver.Status.Lost) {
                return@collectLatest
            } else if (it == ConnectivityObserver.Status.Avaliable) {
                retryAllPagingAdapter()
            }
        }
    }

    private fun showSeeAllPage(uiText: UiText?) {
        binding.apply {
            seeAllPage.animation = slideInLeftAnim()
            recyclerViewSeeAll.layoutManager =
                GridLayoutManager(requireContext(), 2)
            uiText?.let {
                toolbarText.text = it.asString(requireContext())
            }
        }
    }

    private fun hideSeeAllPage() {
        binding.apply {
            scrollView.animation = slideInLeftAnim()
            recyclerViewSeeAll.removeAllViews()
        }
    }

    private fun passTvGenreListToRecyclerAdapter(tvGenreList: List<Genre>) {
        if (tvGenreList.isNotEmpty()) {
            popularTvSeriesAdapter.passMovieGenreList(tvGenreList)
        }
    }

    private fun passMovieGenreListToRecyclerAdapter(movieGenreList: List<Genre>) {
        if (movieGenreList.isNotEmpty()) {
            nowPlayingAdapter.passMovieGenreList(movieGenreList)
            popularMoviesAdapter.passMovieGenreList(movieGenreList)
            topRatedMoviesAdapter.passMovieGenreList(movieGenreList)
            topRatedTvSeriesAdapter.passMovieGenreList(movieGenreList)
        }
    }

    private fun addCallback() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.onEvent(HomeEvent.OnBackPressed)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    private fun setupListenerSeeAllClickEvents() {
        binding.apply {

            nowPlayingSeeAll.setOnClickListener {
                viewModel.onEvent(
                    HomeEvent.ClickSeeAllText(UiText.StringResource(R.string.now_playing))
                )
                recyclerViewSeeAll.adapter = nowPlayingAdapter
            }

            popularMoviesSeeAll.setOnClickListener {
                viewModel.onEvent(
                    HomeEvent.ClickSeeAllText(UiText.StringResource(R.string.popular_movies))
                )
                recyclerViewSeeAll.adapter = popularMoviesAdapter
            }

            popularTvSeeAll.setOnClickListener {
                viewModel.onEvent(
                    HomeEvent.ClickSeeAllText(UiText.StringResource(R.string.popular_tv_series))
                )
                recyclerViewSeeAll.adapter = popularTvSeriesAdapter
            }

            topRatedMoviesSeeAll.setOnClickListener {
                viewModel.onEvent(
                    HomeEvent.ClickSeeAllText(UiText.StringResource(R.string.top_rated_movies))
                )
                recyclerViewSeeAll.adapter = topRatedMoviesAdapter
            }

            topRatedTvSeriesSeeAll.setOnClickListener {
                viewModel.onEvent(
                    HomeEvent.ClickSeeAllText(UiText.StringResource(R.string.top_rated_tv_series))
                )
                recyclerViewSeeAll.adapter = topRatedTvSeriesAdapter
            }
        }

    }

    private fun slideInLeftAnim(): Animation =
        AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_left)

    private fun setupRecyclerAdapters() {
        binding.apply {
            nowPlayingRecyclerView.adapter = nowPlayingAdapter
            nowPlayingRecyclerView.setAlpha(true)
            popularMoviesRecyclerView.adapter = popularMoviesAdapter.withLoadStateFooter(
                footer = MovaLoadStateAdapter { popularMoviesAdapter.retry() }
            )
            topRatedMoviesRecyclerView.adapter = topRatedMoviesAdapter.withLoadStateFooter(
                footer = MovaLoadStateAdapter { topRatedMoviesAdapter.retry() }
            )
            popularTvSeriesRecyclerView.adapter = popularTvSeriesAdapter.withLoadStateFooter(
                footer = MovaLoadStateAdapter { popularTvSeriesAdapter.retry() }
            )
            topRatedTvSeriesRecyclerView.adapter = topRatedTvSeriesAdapter.withLoadStateFooter(
                footer = MovaLoadStateAdapter { topRatedTvSeriesAdapter.retry() }
            )
        }
    }

    private fun setAdaptersClickListener() {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailBottomSheetFragment()
        popularMoviesAdapter.setOnItemClickListener { movie ->
            action.movie = movie
            viewModel.onEvent(HomeEvent.NavigateToDetailBottomSheet(action))
        }

        topRatedMoviesAdapter.setOnItemClickListener { movie ->
            action.movie = movie
            viewModel.onEvent(HomeEvent.NavigateToDetailBottomSheet(action))
        }

        nowPlayingAdapter.setOnClickListener { movie ->
            action.movie = movie
            viewModel.onEvent(HomeEvent.NavigateToDetailBottomSheet(action))
        }

        popularTvSeriesAdapter.setOnItemClickListener { tvSeries ->
            action.tvSeries = tvSeries
            viewModel.onEvent(HomeEvent.NavigateToDetailBottomSheet(action))
        }

        topRatedTvSeriesAdapter.setOnItemClickListener { tvSeries ->
            action.tvSeries = tvSeries
            viewModel.onEvent(HomeEvent.NavigateToDetailBottomSheet(action))
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}