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
import com.e444er.cleanmovie.feature_explore.data.dto.toPersonSearch
import com.e444er.cleanmovie.feature_explore.data.dto.toTvSearch
import com.e444er.cleanmovie.feature_explore.domain.model.MovieSearch
import com.e444er.cleanmovie.feature_explore.domain.util.MediaType
import com.e444er.cleanmovie.feature_explore.presentation.adapter.viewHolder.SearchMovieViewHolder
import com.e444er.cleanmovie.feature_explore.presentation.adapter.viewHolder.SearchPersonViewHolder
import com.e444er.cleanmovie.feature_explore.presentation.adapter.viewHolder.SearchTvViewHolder
import com.e444er.cleanmovie.feature_home.domain.models.Movie
import javax.inject.Inject

class SearchRecyclerAdapter @Inject constructor(
    private val imageLoader: ImageLoader
) :
    PagingDataAdapter<SearchDto, RecyclerView.ViewHolder>(diffCallback = diffCallback) {

    private var movieGenreList: List<Genre> = emptyList()

    private var onItemClickListener: (Movie) -> Unit = {}


    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        item?.let { searchDto ->
            return when (searchDto.mediaType) {
                MediaType.MOVIE.value -> SearchViewType.MOVIE.ordinal
                MediaType.TV_SERIES.value -> SearchViewType.TV.ordinal
                MediaType.PERSON.value -> SearchViewType.PERSON.ordinal
                else -> SearchViewType.MOVIE.ordinal
            }
        }
        return super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val searchDto = getItem(position)
        searchDto?.let {
            when (searchDto.mediaType) {
                MediaType.MOVIE.value -> {
                    val movieSearch = searchDto.toMovieSearch()!!
                    val movieViewHolder = holder as SearchMovieViewHolder
                    movieViewHolder.bindMovie(movieSearch = movieSearch)
                }
                MediaType.TV_SERIES.value -> {
                    val tvSearch = searchDto.toTvSearch()!!
                    val tvViewHolder = holder as SearchTvViewHolder
                    tvViewHolder.bindSearchTv(searchTv = tvSearch)
                }
                MediaType.PERSON.value -> {
                    val personSearch = searchDto.toPersonSearch()!!
                    val searchMovieHolder = holder as SearchPersonViewHolder
                    searchMovieHolder.bindPerson(search = personSearch)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            SearchViewType.MOVIE.ordinal -> {
                SearchMovieViewHolder.from(parent, imageLoader)
            }
            SearchViewType.TV.ordinal -> {
                SearchTvViewHolder.from(parent, imageLoader)
            }
            SearchViewType.PERSON.ordinal -> {
                SearchPersonViewHolder.from(parent, imageLoader)
            }
            else -> {
                SearchTvViewHolder.from(parent, imageLoader)
            }
        }
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

enum class SearchViewType {
    MOVIE,
    TV,
    PERSON
}