package com.e444er.cleanmovie.feature_movie_tv_detail.presentation.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.e444er.cleanmovie.R
import com.e444er.cleanmovie.core.domain.models.Movie
import com.e444er.cleanmovie.core.presentation.util.AlertDialogUtil
import com.e444er.cleanmovie.core.presentation.util.UtilFunctions
import com.e444er.cleanmovie.core.presentation.util.addOnBackPressedCallback
import com.e444er.cleanmovie.core.presentation.util.asString
import com.e444er.cleanmovie.core.util.toolBarTextVisibilityByScrollPositionOfNestedScrollView
import com.e444er.cleanmovie.databinding.FragmentDetailBinding
import com.e444er.cleanmovie.feature_home.presentation.home.HandlePagingLoadStates
import com.e444er.cleanmovie.feature_home.presentation.home.adapter.NowPlayingRecyclerAdapter
import com.e444er.cleanmovie.feature_movie_tv_detail.presentation.detail.adapter.DetailActorAdapter
import com.e444er.cleanmovie.feature_movie_tv_detail.presentation.detail.adapter.TvRecommendationAdapter
import com.e444er.cleanmovie.feature_movie_tv_detail.presentation.detail.adapter.VideosAdapter
import com.e444er.cleanmovie.feature_movie_tv_detail.presentation.detail.event.DetailEvent
import com.e444er.cleanmovie.feature_movie_tv_detail.presentation.detail.event.DetailLoadStateEvent
import com.e444er.cleanmovie.feature_movie_tv_detail.presentation.detail.event.DetailUiEvent
import com.e444er.cleanmovie.feature_movie_tv_detail.presentation.detail.helper.BindMovieDetail
import com.e444er.cleanmovie.feature_movie_tv_detail.presentation.detail.helper.BindTvDetail
import com.e444er.cleanmovie.feature_movie_tv_detail.presentation.util.Constants
import com.e444er.cleanmovie.feature_movie_tv_detail.presentation.util.isSelectedRecommendationTab
import com.e444er.cleanmovie.feature_movie_tv_detail.presentation.util.isSelectedTrailerTab
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private var job: Job? = null
    private var jobMovieId: Job? = null
    private var jobTvId: Job? = null
    private var jobVideos: Job? = null

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var utilFunctions: UtilFunctions


    private val detailActorAdapter: DetailActorAdapter by lazy { DetailActorAdapter() }
    private val movieRecommendationAdapter: NowPlayingRecyclerAdapter by lazy { NowPlayingRecyclerAdapter() }
    private val tvRecommendationAdapter: TvRecommendationAdapter by lazy { TvRecommendationAdapter() }
    private val videosAdapter: VideosAdapter by lazy { VideosAdapter(viewLifecycleOwner.lifecycle) }

    private val viewModel: DetailViewModel by viewModels()

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentDetailBinding.bind(view)

        utilFunctions = UtilFunctions()
        binding.recommendationRecyclerView.adapter = movieRecommendationAdapter
        binding.videosRecyclerView.adapter = videosAdapter

        setupDetailActorAdapter()

        addTabLayoutListener()

        setBtnNavigateUpListener()

        setAdapterListener()

        setTmdbImageOnClickListener()

        setSwipeRefreshListener()

        setDirectorTextListener()

        binding.btnFavoriteList.setOnClickListener {
            viewModel.onEvent(DetailEvent.ClickedAddFavoriteList)
        }

        binding.btnWatchList.setOnClickListener {
            viewModel.onEvent(DetailEvent.ClickedAddWatchList)
        }

        setRecommendationsAdapterListener()

        binding.swipeRefreshLayout.isEnabled = false

        addOnBackPressedCallback(
            activity = requireActivity(),
            onBackPressed = {
                viewModel.onEvent(DetailEvent.OnBackPressed)
            }
        )

        collectDataLifecycleAware()

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

    private fun setRecommendationsAdapterListener() {
        movieRecommendationAdapter.setOnClickListener { movie ->
            viewModel.onEvent(DetailEvent.ClickRecommendationItemClick(movie = movie))
        }

        tvRecommendationAdapter.setOnItemClickListener { tvSeries ->
            viewModel.onEvent(DetailEvent.ClickRecommendationItemClick(tvSeries = tvSeries))
        }
    }

    private fun setBtnNavigateUpListener() {
        binding.btnNavigateUp.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setSwipeRefreshListener() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            job?.cancel()
            collectDataLifecycleAware()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun setDirectorTextListener() {
        binding.creatorDirectorLinearLayout.setOnHierarchyChangeListener(
            object : ViewGroup.OnHierarchyChangeListener {
                override fun onChildViewAdded(p0: View?, director: View?) {
                    director?.setOnClickListener {
                        viewModel.onEvent(
                            DetailEvent.ClickToDirectorName(directorId = director.id)
                        )
                    }
                }

                override fun onChildViewRemoved(p0: View?, p1: View?) {
                    return
                }
            }
        )
    }

    private fun setTmdbImageOnClickListener() {
        binding.imvTmdb.setOnClickListener {
            val tmdbUrl = if (!viewModel.isTvIdEmpty()) {
                "${Constants.TMDB_TV_URL}${viewModel.tvIdState.value}"
            } else {
                "${Constants.TMDB_MOVIE_URL}${viewModel.movieIdState.value}"
            }
            viewModel.onEvent(DetailEvent.IntentToImdbWebSite(tmdbUrl))
        }
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

    private fun collectDataLifecycleAware() {
        job = viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    collectDetailState()
                }
                launch {
                    viewModel.selectedTabPosition.collectLatest { selectedTabPosition ->
                        jobMovieId = launch {
                            jobVideos?.cancel()
                            collectMovieIdState(selectedTabPosition = selectedTabPosition)
                        }

                        jobTvId = launch {
                            jobVideos?.cancel()
                            collectTvIdState(selectedTabPosition = selectedTabPosition)
                        }

                        jobVideos = launch {
                            collectVideos()
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
                            is DetailUiEvent.ShowAlertDialog -> {
                                AlertDialogUtil.showAlertDialog(
                                    context = requireContext(),
                                    title = R.string.sign_in,
                                    message = R.string.must_login_able_to_add_in_list,
                                    positiveBtnMessage = R.string.sign_in,
                                    negativeBtnMessage = R.string.cancel,
                                    onClickPositiveButton = {
                                        findNavController().navigate(DetailFragmentDirections.actionDetailFragmentToLoginFragment())
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private suspend fun collectMovieIdState(selectedTabPosition: Int) {
        viewModel.movieIdState.collectLatest { movieId ->
            if (movieId != Constants.DETAIL_DEFAULT_ID && selectedTabPosition.isSelectedRecommendationTab()) {
                collectMovieRecommendationsAndSwapAdapter(movieId = movieId)
            } else {
                jobMovieId?.cancel()
            }
        }
    }

    private suspend fun collectMovieRecommendationsAndSwapAdapter(movieId: Int) {
        binding.recommendationRecyclerView.swapAdapter(movieRecommendationAdapter, true)
        viewModel.getMovieRecommendations(movieId = movieId)
            .collectLatest { pagingData ->
                movieRecommendationAdapter.submitData(pagingData)
            }
    }

    private suspend fun collectTvIdState(selectedTabPosition: Int) {
        viewModel.tvIdState.collectLatest { tvId ->
            if (tvId != Constants.DETAIL_DEFAULT_ID && selectedTabPosition.isSelectedRecommendationTab()) {
                collectTvRecommendationsAndSwapAdapter(tvId = tvId)
            } else {
                jobTvId?.cancel()
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

    private suspend fun collectVideos() {
        viewModel.videos.collectLatest {
            it?.let { videos ->
                binding.videosRecyclerView.isVisible = videos.result.isNotEmpty()
                binding.txtVideoInfo.isVisible = videos.result.isEmpty()
                videosAdapter.submitList(videos.result)
            }
        }
    }

    private suspend fun collectDetailState() {
        viewModel.detailState.collectLatest { detailState ->
            binding.progressBar.isVisible = detailState.isLoading
            detailState.tvDetail?.let { tvDetail ->
                BindTvDetail(
                    tvDetail = tvDetail,
                    binding = binding,
                    context = requireContext()
                )
                detailActorAdapter.submitList(tvDetail.credit.cast)
            }

            detailState.movieDetail?.let { movieDetail ->
                BindMovieDetail(
                    movieDetail = movieDetail,
                    binding = binding,
                    context = requireContext()
                )
                detailActorAdapter.submitList(movieDetail.credit.cast)
            }

            utilFunctions.setAddFavoriteIcon(
                doesAddFavorite = detailState.doesAddFavorite,
                imageButton = binding.btnFavoriteList
            )

            utilFunctions.setAddWatchListIcon(
                doesAddWatchList = detailState.doesAddWatchList,
                imageButton = binding.btnWatchList
            )

            binding.recommendationShimmerLayout.isVisible = detailState.recommendationLoading

            binding.videosShimmerLayout.isVisible = detailState.videosLoading
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