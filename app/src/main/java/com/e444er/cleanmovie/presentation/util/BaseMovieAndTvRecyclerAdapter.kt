package com.e444er.cleanmovie.presentation.util

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.e444er.cleanmovie.R
import com.e444er.cleanmovie.data.models.Genre
import com.e444er.cleanmovie.databinding.MovieRowBinding
import com.e444er.cleanmovie.domain.models.Movie
import com.e444er.cleanmovie.domain.models.TvSeries
import com.e444er.cleanmovie.presentation.home.adapter.DiffUtilCallBack

abstract class BaseMovieAndTvRecyclerAdapter<T : Any>(
) : PagingDataAdapter<
        T, BaseMovieAndTvRecyclerAdapter.MovieViewHolder>(DiffUtilCallBack<T>()) {


    var itemClickListener: (T) -> Unit = {}

    class MovieViewHolder(
        val binding: MovieRowBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindMovie(
            movie: Movie,
            genreList: List<Genre>,
            context: Context
        ) {

            binding.tvMovieTvName.text = movie.title
            val genre =
                HandleUtils.handleConvertingGenreListToOneGenreString(genreList, movie.genreIds)
            val voteCount = HandleUtils.convertingVoteCountToString(movie.voteCount)
            val releaseDate = HandleUtils.convertToYearFromDate(movie.releaseDate)

            binding.tvReleaseDateGenre.text =
                context.getString(R.string.release_date_genre, releaseDate, genre)

            binding.voteAverage.text = context.getString(
                R.string.voteAverage,
                movie.voteAverage.toString(), voteCount
            )

        }

        fun bindTvSeries(
            tv: TvSeries,
            genreList: List<Genre>,
            context: Context
        ) {
            binding.tvMovieTvName.text = tv.name

            val genre =
                HandleUtils.handleConvertingGenreListToOneGenreString(genreList, tv.genreIds)
            val releaseDate = HandleUtils.convertToYearFromDate(tv.firstAirDate)
            val voteCount = HandleUtils.convertingVoteCountToString(tv.voteCount)

            binding.tvReleaseDateGenre.text =
                context.getString(R.string.release_date_genre, releaseDate, genre)

            binding.voteAverage.text = context.getString(
                R.string.voteAverage,
                tv.voteAverage.toString(),
                voteCount
            )
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val context = holder.itemView.context
        val item = getItem(position)

        if (item is Movie) {

            holder.bindMovie(movie = item, genreList = genreList, context = context)
        }

        if (item is TvSeries) {
            holder.bindTvSeries(tv = item, genreList = genreList, context = context)
        }

        onBindViewHold(binding = holder.binding, position = position, context = context)
    }

    abstract fun onBindViewHold(binding: MovieRowBinding, position: Int, context: Context)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MovieRowBinding.inflate(layoutInflater, parent, false)
        return MovieViewHolder(
            binding = binding
        )
    }

    fun setOnItemClickListener(listener: (T) -> Unit) {
        itemClickListener = listener
    }

    var genreList: List<Genre> = emptyList()

    abstract fun passMovieGenreList(genreList: List<Genre>)
}

