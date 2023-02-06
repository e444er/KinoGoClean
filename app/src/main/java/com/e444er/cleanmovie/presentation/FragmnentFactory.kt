package com.e444er.cleanmovie.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.e444er.cleanmovie.presentation.home.HomeFragment
import com.e444er.cleanmovie.presentation.home.adapter.NowPlayingRecyclerAdapter
import javax.inject.Inject

class FragmentFactory @Inject constructor(
    val nowPlayingRecyclerAdapter: NowPlayingRecyclerAdapter
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            HomeFragment::class.java.name -> HomeFragment(nowPlayingAdapter = nowPlayingRecyclerAdapter)

            else -> super.instantiate(classLoader, className)
        }
    }
}