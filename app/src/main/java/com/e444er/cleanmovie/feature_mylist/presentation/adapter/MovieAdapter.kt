package com.e444er.cleanmovie.feature_mylist.presentation.adapter

import com.e444er.cleanmovie.core.domain.models.Movie

class MovieAdapter : BaseListAdapter<Movie>() {

    override fun onBindViewHolder(holder: BaseListViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(
            context = holder.itemView.context,
            posterPath = movie.posterPath,
            movieTvName = movie.title,
            voteAverage = movie.voteAverage.toString(),
            voteCountByString = movie.voteCountByString,
            releaseDate = movie.releaseDate,
            genreByOne = movie.genreByOne
        )

        holder.itemView.setOnClickListener {
            super.itemClickListener(movie)
        }
    }
}


