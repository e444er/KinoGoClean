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
import com.e444er.cleanmovie.presentation.util.HandleUtils
import javax.inject.Inject

class NowPlayingRecyclerAdapter @Inject constructor(
    private val imageLoader: ImageLoader
) :
    PagingDataAdapter<Movie, NowPlayingRecyclerAdapter.MovieViewHolder>(DiffUtilCallBack<Movie>()) {

    private var movieGenreList: List<Genre> = emptyList()

    private var onItemClickListener: (Movie) -> Unit = {}

    class MovieViewHolder(
        private val binding: NowPlayingRowBinding,
        val movieGenreList: List<Genre>,
        val imageLoader: ImageLoader
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie, context: Context, onItemClickListener: (Movie) -> Unit = {}) {
            binding.movieTitle.text = movie.title

            val voteCount = HandleUtils.handleVoteCount(movie.voteCount)

            binding.voteAverage.text = context.getString(
                R.string.voteAverage,
                movie.voteAverage.toString(), voteCount
            )

            binding.backdropImage.load(
                ImageApi.getImage(
                    imageUrl = movie.posterPath,
                    imageSize = ImageSize.W500.path
                ),
                imageLoader = imageLoader
            )
            if (movie.genreIds.isNotEmpty()) {
                binding.genresText.text =
                    HandleUtils.handleGenreText(movieGenreList = movieGenreList, movie = movie)
            }

            binding.root.setOnClickListener {
                onItemClickListener.invoke(movie)
            }
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null) {
            val context = holder.itemView.context
            holder.bind(movie = movie, context = context, onItemClickListener = onItemClickListener)
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

    fun setOnClickListener(listener: (Movie) -> Unit) {
        onItemClickListener = listener
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