package com.e444er.cleanmovie.feature_movie_tv_detail.presentation.detail.adapter

import android.content.Context
import coil.load
import com.e444er.cleanmovie.core.data.data_source.remote.ImageApi
import com.e444er.cleanmovie.core.data.data_source.remote.ImageSize
import com.e444er.cleanmovie.core.presentation.util.BaseMovieAndTvRecyclerAdapter
import com.e444er.cleanmovie.databinding.MovieRowBinding
import com.e444er.cleanmovie.feature_home.domain.models.TvSeries

class TvRecommendationAdapter : BaseMovieAndTvRecyclerAdapter<TvSeries>() {

    override fun onBindViewHold(binding: MovieRowBinding, position: Int, context: Context) {
        val tvSeries = getItem(position)

        if (tvSeries != null) {
            binding.ivPoster.load(
                ImageApi.getImage(
                    imageSize = ImageSize.W185.path,
                    imageUrl = tvSeries.posterPath
                )
            )

            binding.root.setOnClickListener {
                this.itemClickListener(tvSeries)
            }
        }
    }
}