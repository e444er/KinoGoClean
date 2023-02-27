package com.e444er.cleanmovie.feature_movie_tv_detail.presentation.detail.helper

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.e444er.cleanmovie.R
import com.e444er.cleanmovie.databinding.FragmentDetailBinding
import com.e444er.cleanmovie.feature_movie_tv_detail.domain.models.detail.CreatedBy
import com.e444er.cleanmovie.feature_movie_tv_detail.domain.models.detail.TvDetail
import com.google.android.material.textview.MaterialTextView

class BindTvDetail(
    binding: FragmentDetailBinding,
    tvDetail: TvDetail,
    context: Context
) : BindAttributesDetailFragment(binding = binding, context = context) {

    init {
        bindImage(posterPath = tvDetail.posterPath)
        removeDirectorsInLayout()
        bindDetailInfoSection(
            voteAverage = tvDetail.voteAverage,
            voteCount = tvDetail.voteCount,
            ratingBarValue = tvDetail.ratingValue,
            genreList = tvDetail.genres
        )
        bindToolBarTitle(toolbarTitle = tvDetail.name)
        bindFilmName(filmName = tvDetail.name)
        bindReleaseDate(releaseDate = tvDetail.releaseDate)
        bindOverview(overview = tvDetail.overview)
        bindWatchProviders(providerRegion = tvDetail.watchProviders.results)
        bindCreatorNames(createdBy = tvDetail.createdBy)
        showSeasonText(season = tvDetail.numberOfSeasons)
        hideRuntimeTextAndClockIcon()
    }

    private fun bindCreatorNames(createdBy: List<CreatedBy>) {
        if (createdBy.isEmpty()) {
            binding.creatorDirectorLinearLayout.removeAllViews()
            return
        }
        setCreatorNameByCountOfCreator(creatorCount = createdBy.count())
        createdBy.forEach { creator ->
            val creatorText = LayoutInflater.from(context)
                .inflate(
                    R.layout.creator_text,
                    binding.creatorDirectorLinearLayout,
                    false
                ) as MaterialTextView
            creatorText.text = creator.name
            creatorText.id = creator.id
            binding.creatorDirectorLinearLayout.addView(creatorText)
        }
    }

    private fun setCreatorNameByCountOfCreator(creatorCount: Int) {
        binding.txtDirectorOrCreatorName.text = if (creatorCount > 1) {
            context.getString(R.string.plural_creator_title)
        } else {
            context.getString(R.string.singular_creator_title)
        }
    }

    private fun showSeasonText(season: Int) {
        binding.apply {
            imvCircle.visibility = View.VISIBLE
            txtSeason.visibility = View.VISIBLE
            txtSeason.text = context.getString(
                R.string.season_count,
                season.toString()
            )
        }
    }

    private fun hideRuntimeTextAndClockIcon() {
        binding.txtRuntime.visibility = View.GONE
        binding.imvClockIcon.visibility = View.GONE
    }
}