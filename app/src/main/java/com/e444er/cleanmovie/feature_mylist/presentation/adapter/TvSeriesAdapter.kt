package com.e444er.cleanmovie.feature_mylist.presentation.adapter

import com.e444er.cleanmovie.core.domain.models.TvSeries


class TvSeriesAdapter : BaseListAdapter<TvSeries>() {

    override fun onBindViewHolder(holder: BaseListViewHolder, position: Int) {
        val tvSeries = getItem(position)
        holder.bind(
            context = holder.itemView.context,
            posterPath = tvSeries.posterPath,
            movieTvName = tvSeries.originalName,
            voteAverage = tvSeries.voteAverage.toString(),
            voteCountByString = tvSeries.voteCountByString,
            releaseDate = tvSeries.firstAirDate,
            genreByOne = tvSeries.genreByOne
        )

        holder.itemView.setOnClickListener {
            super.itemClickListener(tvSeries)
        }
    }
}