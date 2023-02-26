package com.e444er.cleanmovie.feature_explore.presentation.adapter

import android.content.Context
import android.view.View
import coil.ImageLoader
import coil.load
import com.e444er.cleanmovie.R
import com.e444er.cleanmovie.core.data.data_source.remote.ImageApi
import com.e444er.cleanmovie.core.data.data_source.remote.ImageSize
import com.e444er.cleanmovie.core.presentation.util.BaseMovieAndTvRecyclerAdapter
import com.e444er.cleanmovie.databinding.MovieRowBinding
import com.e444er.cleanmovie.feature_home.domain.models.Movie
import javax.inject.Inject


class FilterMoviesAdapter @Inject constructor(
    private val imageLoader: ImageLoader
) : BaseMovieAndTvRecyclerAdapter<Movie>() {

    override fun onBindViewHold(
        binding: MovieRowBinding,
        position: Int,
        context: Context
    ) {

        val movie = getItem(position)

        if (movie != null) {
            binding.ivPoster.load(
                ImageApi.getImage(
                    imageSize = ImageSize.W185.path,
                    imageUrl = movie.posterPath
                ),
                imageLoader = imageLoader
            )

            binding.root.setOnClickListener {
                this.itemClickListener(movie)
            }

            binding.root.setOnClickListener {
                this.itemClickListener(movie)
            }
            binding.txtCategory.visibility = View.VISIBLE
            binding.txtCategory.text = context.getText(R.string.movie)
        }

    }
}
