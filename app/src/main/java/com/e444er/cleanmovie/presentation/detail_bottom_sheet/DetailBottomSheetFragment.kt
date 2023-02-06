package com.e444er.cleanmovie.presentation.detail_bottom_sheet

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.ImageLoader
import coil.load
import com.e444er.cleanmovie.R
import com.e444er.cleanmovie.data.remote.ImageApi
import com.e444er.cleanmovie.data.remote.ImageSize
import com.e444er.cleanmovie.databinding.FragmentDetailBottomSheetBinding
import com.e444er.cleanmovie.presentation.util.Util
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentDetailBottomSheetBinding? = null
    private val binding get() = _binding

    private val arguments: DetailBottomSheetFragmentArgs by navArgs()

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDetailBottomSheetBinding.inflate(inflater, container, false)
        _binding = binding

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movie = arguments.movie
        val tvSeries = arguments.tvSeries

        setupButtonClickListeners()

        binding?.apply {
            tvOverview.movementMethod = ScrollingMovementMethod()

            if (movie != null){
                tvName.text = movie.title
                tvReleaseDate.text = Util.handleReleaseDate(movie.releaseDate)
                tvOverview.text = movie.overview
                if (movie.posterPath != null) {
                    loadImage(posterPath = movie.posterPath)
                }
                tvBottomInfoText.text =
                    requireContext().getString(R.string.detail_bottom_sheet_movie_info)
            }

            if (tvSeries != null) {

                tvName.text = tvSeries.name
                tvOverview.text = tvSeries.overview
                tvReleaseDate.text = Util.handleReleaseDate(tvSeries.firstAirDate)
                if (tvSeries.posterPath != null) {
                    loadImage(posterPath = tvSeries.posterPath)
                }
                tvBottomInfoText.text =
                    requireContext().getString(R.string.detail_bottom_sheet_tv_info)

            }
        }
    }

    private fun loadImage(posterPath: String) {
        binding?.let {
            it.ivPoster.load(
                ImageApi.getImage(
                    imageSize = ImageSize.W185.path,
                    imageUrl = posterPath
                )
            ) {
                imageLoader
            }
        }
    }

    private fun setupButtonClickListeners() {
        binding?.apply {
            ibClose.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}