package com.e444er.cleanmovie.presentation.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.e444er.cleanmovie.presentation.detail_bottom_sheet.DetailBottomSheetFragment
import com.e444er.cleanmovie.presentation.explore.ExploreFragment
import com.e444er.cleanmovie.presentation.filter_bottom_sheet.FilterBottomSheetFragment
import com.e444er.cleanmovie.presentation.home.HomeFragment
import com.e444er.cleanmovie.presentation.home.adapter.*
import javax.inject.Inject

class FragmentFactory@Inject constructor(
    private val nowPlayingRecyclerAdapter: NowPlayingRecyclerAdapter,
    private val popularMoviesAdapter: PopularMoviesAdapter,
    private val topRatedMoviesAdapter: TopRatedMoviesAdapter,
    private val popularTvSeriesAdapter: PopularTvSeriesAdapter,
    private val topRatedTvSeriesAdapter: TopRatedTvSeriesAdapter
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            HomeFragment::class.java.name -> HomeFragment(
                nowPlayingAdapter = nowPlayingRecyclerAdapter,
                popularMoviesAdapter = popularMoviesAdapter,
                topRatedMoviesAdapter = topRatedMoviesAdapter,
                popularTvSeriesAdapter = popularTvSeriesAdapter,
                topRatedTvSeriesAdapter = topRatedTvSeriesAdapter
            )

            ExploreFragment::class.java.name -> ExploreFragment()
            DetailBottomSheetFragment::class.java.name -> DetailBottomSheetFragment()
            FilterBottomSheetFragment::class.java.name -> FilterBottomSheetFragment()
            else -> super.instantiate(classLoader, className)
        }
    }
}