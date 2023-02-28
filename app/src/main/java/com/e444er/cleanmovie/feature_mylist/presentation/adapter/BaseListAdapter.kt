package com.e444er.cleanmovie.feature_mylist.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.e444er.cleanmovie.R
import com.e444er.cleanmovie.core.data.data_source.remote.ImageApi
import com.e444er.cleanmovie.core.data.data_source.remote.ImageSize
import com.e444er.cleanmovie.databinding.MovieRowBinding
import com.e444er.cleanmovie.feature_home.presentation.home.adapter.DiffUtilCallBack

abstract class BaseListAdapter<T : Any> :
    ListAdapter<T, BaseListAdapter.BaseListViewHolder>(DiffUtilCallBack<T>()) {

    var itemClickListener: (T) -> Unit = {}

    class BaseListViewHolder(
        private val binding: MovieRowBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            context: Context,
            posterPath: String?,
            movieTvName: String,
            voteAverage: String,
            voteCountByString: String,
            releaseDate: String?,
            genreByOne: String
        ) {
            binding.ivPoster.load(
                ImageApi.getImage(
                    imageSize = ImageSize.W185.path,
                    imageUrl = posterPath
                )
            )
            binding.tvMovieTvName.text = movieTvName

            binding.voteAverage.text = context.getString(
                R.string.voteAverage,
                voteAverage,
                voteCountByString
            )

            releaseDate?.let {
                binding.tvReleaseDateGenre.text =
                    context.getString(R.string.release_date_genre, releaseDate, genreByOne)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MovieRowBinding.inflate(layoutInflater, parent, false)
        return BaseListViewHolder(
            binding = binding
        )
    }


    fun setOnclickListener(listener: (T) -> Unit) {
        itemClickListener = listener
    }
}