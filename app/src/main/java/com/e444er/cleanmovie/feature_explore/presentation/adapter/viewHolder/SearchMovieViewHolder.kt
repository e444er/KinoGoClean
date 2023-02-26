package com.e444er.cleanmovie.feature_explore.presentation.adapter.viewHolder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.load
import com.e444er.cleanmovie.R
import com.e444er.cleanmovie.core.data.data_source.remote.ImageApi
import com.e444er.cleanmovie.core.data.data_source.remote.ImageSize
import com.e444er.cleanmovie.databinding.NowPlayingRowBinding
import com.e444er.cleanmovie.feature_explore.domain.model.MovieSearch

class SearchMovieViewHolder(
    private val binding: NowPlayingRowBinding,
    val imageLoader: ImageLoader,
    val context: Context
) : RecyclerView.ViewHolder(binding.root) {

    fun bindMovie(movieSearch: MovieSearch) {
        binding.backdropImage.load(
            ImageApi.getImage(
                imageUrl = movieSearch.posterPath,
                imageSize = ImageSize.W500.path
            ),
            imageLoader = imageLoader
        )


        binding.voteAverage.text = context.getString(
            R.string.voteAverage,
            movieSearch.voteAverage.toString(),
            movieSearch.voteCountByString
        )

        binding.genresText.text = movieSearch.genreByOneForMovie

        binding.movieTitle.textSize = 16f
        binding.movieTitle.text = movieSearch.title
        binding.txtCategory.visibility = View.VISIBLE
        binding.txtCategory.text = context.getString(R.string.movie)
    }

    companion object {

        fun from(
            parent: ViewGroup,
            imageLoader: ImageLoader,
        ): SearchMovieViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = NowPlayingRowBinding.inflate(layoutInflater, parent, false)
            return SearchMovieViewHolder(
                binding = binding,
                imageLoader = imageLoader,
                context = parent.context
            )
        }
    }
}