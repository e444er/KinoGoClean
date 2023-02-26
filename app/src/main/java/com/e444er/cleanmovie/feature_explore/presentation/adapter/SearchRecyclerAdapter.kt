package com.e444er.cleanmovie.feature_explore.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.load
import com.e444er.cleanmovie.R
import com.e444er.cleanmovie.core.data.data_source.remote.ImageApi
import com.e444er.cleanmovie.core.data.data_source.remote.ImageSize
import com.e444er.cleanmovie.core.data.dto.Genre
import com.e444er.cleanmovie.databinding.NowPlayingRowBinding
import com.e444er.cleanmovie.feature_explore.data.dto.SearchDto
import com.e444er.cleanmovie.feature_explore.data.dto.toMovieSearch
import com.e444er.cleanmovie.feature_explore.domain.model.MovieSearch
import com.e444er.cleanmovie.feature_explore.domain.util.MediaType
import com.e444er.cleanmovie.feature_home.domain.models.Movie
import javax.inject.Inject

class SearchRecyclerAdapter @Inject constructor(
    private val imageLoader: ImageLoader
) :
    PagingDataAdapter<SearchDto, SearchRecyclerAdapter.MovieViewHolder>(diffCallback = diffCallback) {

    private var movieGenreList: List<Genre> = emptyList()

    private var onItemClickListener: (Movie) -> Unit = {}

    class MovieViewHolder(
        private val binding: NowPlayingRowBinding,
        val imageLoader: ImageLoader,
        val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindMovie(movieSearch: MovieSearch) {
            binding.backdropImage.load(
                ImageApi.getImage(
                    imageUrl = movieSearch.posterPath,
                    imageSize = ImageSize.W500.path
                ),
                imageLoader = imageLoader
            )

            binding.movieTitle.textSize = 16f
            binding.movieTitle.text = movieSearch.title
            binding.txtCategory.visibility = View.VISIBLE
            binding.txtCategory.text = context.getString(R.string.movie)
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val searchDto = getItem(position)
        searchDto?.let {
            if (searchDto.mediaType == MediaType.MOVIE.value) {
                val movieSearch = searchDto.toMovieSearch()!!
                holder.itemView.id = movieSearch.id
                holder.bindMovie(movieSearch)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = NowPlayingRowBinding.inflate(layoutInflater, parent, false)
        return MovieViewHolder(
            binding = binding,
            imageLoader = imageLoader,
            context = parent.context
        )
    }
}

val diffCallback = object : DiffUtil.ItemCallback<SearchDto>() {
    override fun areItemsTheSame(oldItem: SearchDto, newItem: SearchDto): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SearchDto, newItem: SearchDto): Boolean {
        return oldItem == newItem
    }

}