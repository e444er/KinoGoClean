package com.e444er.cleanmovie.feature_movie_tv_detail.presentation.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import coil.ImageLoader
import com.e444er.cleanmovie.R
import com.e444er.cleanmovie.core.presentation.util.asString
import com.e444er.cleanmovie.core.util.toolBarTextVisibilityByScrollPositionOfNestedScrollView
import com.e444er.cleanmovie.databinding.FragmentDetailBinding
import com.e444er.cleanmovie.feature_home.domain.models.Movie
import com.e444er.cleanmovie.feature_home.presentation.home.HandlePagingLoadStates
import com.e444er.cleanmovie.feature_home.presentation.home.adapter.NowPlayingRecyclerAdapter
import com.e444er.cleanmovie.feature_movie_tv_detail.presentation.detail.adapter.DetailActorAdapter
import com.e444er.cleanmovie.feature_movie_tv_detail.presentation.detail.adapter.TvRecommendationAdapter
import com.e444er.cleanmovie.feature_movie_tv_detail.presentation.detail.adapter.VideosAdapter
import com.e444er.cleanmovie.feature_movie_tv_detail.presentation.detail.event.DetailEvent
import com.e444er.cleanmovie.feature_movie_tv_detail.presentation.detail.event.DetailLoadStateEvent
import com.e444er.cleanmovie.feature_movie_tv_detail.presentation.detail.event.DetailUiEvent
import com.e444er.cleanmovie.feature_movie_tv_detail.presentation.detail.helper.BindAttributesDetailFrag
import com.e444er.cleanmovie.feature_movie_tv_detail.presentation.util.Constants
import com.e444er.cleanmovie.feature_movie_tv_detail.presentation.util.isSelectedRecommendationTab
import com.e444er.cleanmovie.feature_movie_tv_detail.presentation.util.isSelectedTrailerTab
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private var job: Job? = null
    private var jobMovieId: Job? = null
    private var jobTvId: Job? = null
    private var jobVideos: Job? = null

    private lateinit var bindAttributesDetailFrag: BindAttributesDetailFrag

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var imageLoader: ImageLoader

    private val detailActorAdapter: DetailActorAdapter by lazy { DetailActorAdapter() }
    private val movieRecommendationAdapter: NowPlayingRecyclerAdapter by lazy { NowPlayingRecyclerAdapter() }
    private val tvRecommendationAdapter: TvRecommendationAdapter by lazy { TvRecommendationAdapter() }
    private val videosAdapter: VideosAdapter by lazy { VideosAdapter(viewLifecycleOwner.lifecycle) }

    private val viewModel: DetailViewModel by viewModels()

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentDetailBinding.bind(view)
        binding.recommendationRecyclerView.adapter = movieRecommendationAdapter
        binding.videosRecyclerView.adapter = videosAdapter
        setupDetailActorAdapter()

        addTabLayoutListener()

        setBindAttributesDetailFrag()

        binding.swipeRefreshLayout.isEnabled = false

        addOnBackPressedCallback()

        collectDataLifecycleAware()

        setAdapterListener()

        toolBarTextVisibilityByScrollPositionOfNestedScrollView(
            nestedScrollView = binding.nestedScrollView,
            position = 1500,
            toolBarTitle = binding.txtToolBarTitle,
            toolbar = binding.toolbar,
            context = requireContext()
        )

        handleTvRecommendationsPagingLoadStates()
        handleMovieRecommendationsPagingLoadStates()
    }

    private fun setBindAttributesDetailFrag() {
        bindAttributesDetailFrag = BindAttributesDetailFrag(
            binding = binding,
            imageLoader = imageLoader,
            context = requireContext(),
            onClickTmdbImage = { tmdbUrl ->
                viewModel.onEvent(DetailEvent.IntentToImdbWebSite(tmdbUrl))
            },
            onClickDirectorName = { directorId ->
                viewModel.onEvent(
                    DetailEvent.ClickToDirectorName(directorId = directorId)
                )
            },
            onNavigateUp = {
                findNavController().popBackStack()
            },
            onSwipeRefresh = {
                job?.cancel()
                collectDataLifecycleAware()
                binding.swipeRefreshLayout.isRefreshing = false
            }
        )
    }

    private fun addTabLayoutListener() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    binding.recommendationRecyclerView.removeAllViews()
                    viewModel.onEvent(DetailEvent.SelectedTab(tab.position))
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

    }

    private fun setAdapterListener() {
        detailActorAdapter.setActorTextListener { actorId ->
            viewModel.onEvent(DetailEvent.ClickActorName(actorId = actorId))
        }
    }

    private fun setupDetailActorAdapter() {
        binding.recyclerViewActor.adapter = detailActorAdapter
    }

    private fun addOnBackPressedCallback() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.onEvent(DetailEvent.OnBackPressed)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    private fun collectDataLifecycleAware() {
        job = viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    collectDetailState()
                }
                launch {
                    viewModel.selectedTabPosition.collectLatest { selectedTabPosition ->
                        jobMovieId = launch {
                            collectMovieIdState(selectedTabPosition = selectedTabPosition)
                        }
                        jobTvId = launch {
                            collectTvIdState(selectedTabPosition = selectedTabPosition)
                        }
                        jobVideos = launch {
                            viewModel.videos.collectLatest {
                                it?.let { videos ->
                                    binding.videosRecyclerView.isVisible =
                                        videos.result.isNotEmpty()
                                    binding.txtVideoInfo.isVisible = videos.result.isEmpty()
                                    videosAdapter.submitList(videos.result)
                                }
                            }
                        }

                        if (selectedTabPosition.isSelectedTrailerTab()) {
                            jobTvId?.cancel()
                            jobMovieId?.cancel()
                            binding.recommendationRecyclerView.isVisible = false
                            binding.videosRecyclerView.isVisible = true
                        } else {
                            jobVideos?.cancel()
                            binding.videosRecyclerView.isVisible = false
                            binding.recommendationRecyclerView.isVisible = true
                        }
                    }
                }

                launch {
                    viewModel.eventUiFlow.collectLatest { uiEvent ->
                        when (uiEvent) {
                            is DetailUiEvent.PopBackStack -> {
                                findNavController().popBackStack()
                            }
                            is DetailUiEvent.ShowSnackbar -> {
                                binding.swipeRefreshLayout.isEnabled = true
                                Snackbar.make(
                                    requireView(),
                                    uiEvent.uiText.asString(requireContext()),
                                    Snackbar.LENGTH_SHORT
                                ).show()
                            }
                            is DetailUiEvent.IntentToImdbWebSite -> {
                                intentToTmdbWebSite(uiEvent.url)
                            }
                            is DetailUiEvent.NavigateTo -> {
                                findNavController().navigate(uiEvent.directions)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun handleTvRecommendationsPagingLoadStates() {
        HandlePagingLoadStates(
            pagingAdapter = tvRecommendationAdapter,
            onLoading = { viewModel.onAdapterLoadStateEvent(DetailLoadStateEvent.RecommendationLoading) },
            onNotLoading = { viewModel.onAdapterLoadStateEvent(DetailLoadStateEvent.RecommendationNotLoading) },
            onError = { viewModel.onAdapterLoadStateEvent(DetailLoadStateEvent.PagingError(it)) }
        )
    }

    private fun handleMovieRecommendationsPagingLoadStates() {
        HandlePagingLoadStates<Movie>(
            nowPlayingRecyclerAdapter = movieRecommendationAdapter,
            onLoading = { viewModel.onAdapterLoadStateEvent(DetailLoadStateEvent.RecommendationLoading) },
            onNotLoading = { viewModel.onAdapterLoadStateEvent(DetailLoadStateEvent.RecommendationNotLoading) },
            onError = { viewModel.onAdapterLoadStateEvent(DetailLoadStateEvent.PagingError(it)) }
        )
    }

    private suspend fun collectMovieRecommendationsAndSwapAdapter(movieId: Int) {
        binding.recommendationRecyclerView.swapAdapter(movieRecommendationAdapter, true)
        viewModel.getMovieRecommendations(movieId = movieId)
            .collectLatest { pagingData ->
                movieRecommendationAdapter.submitData(pagingData)
            }
    }

    private suspend fun collectMovieIdState(selectedTabPosition: Int) {
        viewModel.movieIdState.collectLatest { movieId ->
            if (movieId != Constants.DETAIL_DEFAULT_ID && selectedTabPosition.isSelectedRecommendationTab()) {
                jobVideos?.cancel()
                collectMovieRecommendationsAndSwapAdapter(movieId = movieId)
            } else {
                jobMovieId?.cancel()
            }
        }
    }

    private suspend fun collectTvRecommendationsAndSwapAdapter(tvId: Int) {
        binding.recommendationRecyclerView.swapAdapter(tvRecommendationAdapter, true)
        viewModel.getTvRecommendations(tvId = tvId)
            .collectLatest { pagingData ->
                tvRecommendationAdapter.submitData(pagingData)
            }
    }

    private suspend fun collectTvIdState(selectedTabPosition: Int) {
        viewModel.tvIdState.collectLatest { tvId ->
            if (tvId != Constants.DETAIL_DEFAULT_ID && selectedTabPosition.isSelectedRecommendationTab()) {
                collectTvRecommendationsAndSwapAdapter(tvId = tvId)
                jobVideos?.cancel()
            } else {
                jobTvId?.cancel()
            }
        }
    }


    private suspend fun collectDetailState() {
        viewModel.detailState.collectLatest { detailState ->
            binding.progressBar.isVisible = detailState.loading
            detailState.tvDetail?.let {
                bindAttributesDetailFrag.bindTvDetail(
                    tvDetail = it
                )
                detailActorAdapter.submitList(it.credit.cast)
            }

            detailState.movieDetail?.let { movieDetail ->
                bindAttributesDetailFrag.bindMovieDetail(
                    movieDetail = movieDetail
                )
                detailActorAdapter.submitList(movieDetail.credit.cast)
            }

            binding.recommendationShimmerLayout.isVisible = detailState.recommendationLoading

            binding.videosShimmerLayout.isVisible = detailState.videosLoading
        }
    }

    private fun intentToTmdbWebSite(tmdbUrl: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(tmdbUrl)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}