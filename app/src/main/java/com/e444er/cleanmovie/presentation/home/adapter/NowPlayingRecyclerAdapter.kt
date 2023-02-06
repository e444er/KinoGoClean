package com.e444er.cleanmovie.presentation.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.load
import com.e444er.cleanmovie.R
import com.e444er.cleanmovie.data.remote.ImageApi
import com.e444er.cleanmovie.data.remote.ImageSize
import com.e444er.cleanmovie.databinding.NowPlayingRowBinding
import com.e444er.cleanmovie.domain.models.Genre
import com.e444er.cleanmovie.domain.models.Movie
import com.e444er.cleanmovie.domain.models.TvSeries
import javax.inject.Inject

class NowPlayingRecyclerAdapter @Inject constructor(
    private val imageLoader: ImageLoader
) : PagingDataAdapter<Movie, NowPlayingRecyclerAdapter.MovieViewHolder>(DiffUtilCallBack()) {

    private var movieGenreList: List<Genre> = emptyList()
    class MovieViewHolder(
        private val binding: NowPlayingRowBinding,
        val movieGenreList: List<Genre>,
        val imageLoader: ImageLoader
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie, context: Context) {

            binding.movieTitle.text = movie.title
            binding.voteAverage.text = context.getString(
                R.string.voteAverage,
                movie.voteAverage.toString(), movie.voteCount.toString()
            )
            binding.backdropImage.load(
                ImageApi.getImage(
                    imageUrl = movie.posterPath,
                    imageSize = ImageSize.W500.path
                ),
                imageLoader = imageLoader
            )
            if (movie.genreIds.isNotEmpty()) {

                binding.genresText.text = handleGenreText(movie = movie)
            }
        }


        //genre
        private fun handleGenreText(movie: Movie): String {
            var genreNames = ""
            movieGenreList.forEach { genre ->
                movie.genreIds.forEach {
                    if (it == genre.id) {
                        genreNames += "${genre.name}, "
                    }
                }
            }

            return genreNames.substring(startIndex = 0, endIndex = genreNames.length - 2)
        }

    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null) {
            val context = holder.itemView.context
            holder.bind(movie = movie, context = context)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = NowPlayingRowBinding.inflate(layoutInflater, parent, false)
        return MovieViewHolder(
            binding,
            movieGenreList = movieGenreList,
            imageLoader = imageLoader
        )
    }

    fun passMovieGenreList(movieGenreList: List<Genre>) {
        this.movieGenreList = movieGenreList
    }
}

class DiffUtilCallBack<T : Any> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return if (oldItem is Movie && newItem is Movie) {
            val old = oldItem as Movie
            val new = newItem as Movie
            new.id == old.id
        } else {
            val old = oldItem as TvSeries
            val new = newItem as TvSeries
            old.id == new.id
        }
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return if (oldItem is Movie) {
            oldItem as Movie == newItem as Movie
        } else {
            oldItem as TvSeries == newItem as TvSeries
        }
    }


}