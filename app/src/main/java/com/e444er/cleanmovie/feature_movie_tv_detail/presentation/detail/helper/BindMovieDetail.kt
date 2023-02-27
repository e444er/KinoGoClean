package com.e444er.cleanmovie.feature_movie_tv_detail.presentation.detail.helper

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.e444er.cleanmovie.R
import com.e444er.cleanmovie.databinding.FragmentDetailBinding
import com.e444er.cleanmovie.feature_movie_tv_detail.domain.models.credit.Crew
import com.e444er.cleanmovie.feature_movie_tv_detail.domain.models.detail.MovieDetail
import com.e444er.cleanmovie.feature_movie_tv_detail.presentation.util.Constants
import com.google.android.material.textview.MaterialTextView

class BindMovieDetail(
    binding: FragmentDetailBinding,
    movieDetail: MovieDetail,
    context: Context
) : BindAttributesDetailFragment(binding = binding, context = context) {

    private val DIRECTION_DEPARTMENT_NAME = "Directing"

    init {
        bindImage(posterPath = movieDetail.posterPath)
        removeDirectorsInLayout()
        bindFilmName(filmName = movieDetail.title)
        bindOverview(overview = movieDetail.overview)
        bindWatchProviders(providerRegion = movieDetail.watchProviders.results)
        bindToolBarTitle(toolbarTitle = movieDetail.title)
        bindDetailInfoSection(
            voteAverage = movieDetail.voteAverage,
            voteCount = movieDetail.voteCount,
            ratingBarValue = movieDetail.ratingValue,
            genreList = movieDetail.genres
        )
        bindMovieRuntime(convertedRuntime = movieDetail.convertedRuntime)
        bindDirectorName(crews = movieDetail.credit.crew)
        bindReleaseDate(releaseDate = movieDetail.releaseDate)
        hideSeasonText()
        showRuntimeTextAndClockIcon()
    }


    private fun hideSeasonText() {
        binding.apply {
            imvCircle.visibility = View.GONE
            txtSeason.visibility = View.GONE
        }
    }

    private fun showRuntimeTextAndClockIcon() {
        binding.txtRuntime.visibility = View.VISIBLE
        binding.imvClockIcon.visibility = View.VISIBLE
    }

    private fun bindMovieRuntime(convertedRuntime: Map<String, String>) {
        if (convertedRuntime.isNotEmpty()) {
            binding.txtRuntime.text = context.getString(
                R.string.runtime,
                convertedRuntime[Constants.HOUR_KEY],
                convertedRuntime[Constants.MINUTES_KEY]
            )
        }
    }

    private fun bindDirectorName(crews: List<Crew>) {
        val director = crews.find {
            it.department == DIRECTION_DEPARTMENT_NAME
        }
        director?.let {
            binding.txtDirectorOrCreatorName.text = context.getString(R.string.director_title)
            val directorText = LayoutInflater.from(context)
                .inflate(
                    R.layout.creator_text,
                    binding.creatorDirectorLinearLayout,
                    false
                ) as MaterialTextView

            directorText.text = director.name
            directorText.id = director.id
            binding.creatorDirectorLinearLayout.addView(directorText)
        }
    }
}