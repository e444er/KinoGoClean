package com.e444er.cleanmovie.presentation.home.adapter

import android.content.Context
import coil.ImageLoader
import coil.load
import com.e444er.cleanmovie.R
import com.e444er.cleanmovie.data.remote.ImageApi
import com.e444er.cleanmovie.data.remote.ImageSize
import com.e444er.cleanmovie.databinding.MovieRowBinding
import com.e444er.cleanmovie.domain.models.Genre
import com.e444er.cleanmovie.domain.models.Movie
import com.e444er.cleanmovie.presentation.util.BaseMovieAndTvRecyclerAdapter
import com.e444er.cleanmovie.presentation.util.Util
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

            binding.tvMovieTvName.text = movie.title

            val genre = Util.handleGenreOneText(genreList, movie.genreIds)
            val releaseDate = Util.handleReleaseDate(movie.releaseDate)

            val voteCount = Util.handleVoteCount(movie.voteCount)

            binding.tvReleaseDateGenre.text =
                context.getString(R.string.release_date_genre, releaseDate, genre)

            binding.voteAverage.text = context.getString(
                R.string.voteAverage,
                movie.voteAverage.toString(),
                voteCount
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