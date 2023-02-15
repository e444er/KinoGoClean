package com.e444er.cleanmovie.presentation.home.adapter

import android.content.Context
import coil.ImageLoader
import coil.load
import com.e444er.cleanmovie.data.remote.ImageApi
import com.e444er.cleanmovie.data.remote.ImageSize
import com.e444er.cleanmovie.databinding.MovieRowBinding

import com.e444er.cleanmovie.data.models.Genre
import com.e444er.cleanmovie.domain.models.TvSeries
import com.e444er.cleanmovie.presentation.util.BaseMovieAndTvRecyclerAdapter
import javax.inject.Inject

class TopRatedTvSeriesAdapter @Inject constructor(
    private val imageLoader: ImageLoader
) : BaseMovieAndTvRecyclerAdapter<TvSeries>() {

    override fun onBindViewHold(binding: MovieRowBinding, position: Int, context: Context) {
        val tvSeries = getItem(position)

        if (tvSeries != null) {
            binding.ivPoster.load(
                ImageApi.getImage(
                    imageSize = ImageSize.W185.path,
                    imageUrl = tvSeries.posterPath
                ),
                imageLoader = imageLoader
            )

            binding.root.setOnClickListener {
                this.itemClickListener(tvSeries)
            }
        }
    }

    override fun passMovieGenreList(genreList: List<Genre>) {
        this.genreList = genreList
    }
}