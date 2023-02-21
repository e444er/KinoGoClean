package com.e444er.cleanmovie.feature_home.presentation.home.recyler

import android.content.Context
import coil.ImageLoader
import coil.load
import com.e444er.cleanmovie.core.data.data_source.remote.ImageApi
import com.e444er.cleanmovie.core.data.data_source.remote.ImageSize
import com.e444er.cleanmovie.core.data.dto.Genre
import com.e444er.cleanmovie.core.presentation.util.BaseMovieAndTvRecyclerAdapter
import com.e444er.cleanmovie.databinding.MovieRowBinding
import com.e444er.cleanmovie.feature_home.domain.models.Movie
import javax.inject.Inject

class PopularMoviesAdapter @Inject constructor(
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
        }

    }


    override
    fun passMovieGenreList(genreList: List<Genre>) {
        this.genreList = genreList
    }
}