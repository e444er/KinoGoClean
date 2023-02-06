package com.e444er.cleanmovie.presentation.util

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.e444er.cleanmovie.databinding.MovieRowBinding
import com.e444er.cleanmovie.domain.models.Genre
import com.e444er.cleanmovie.presentation.home.adapter.DiffUtilCallBack

abstract class BaseMovieAndTvRecyclerAdapter<T : Any>(
) : PagingDataAdapter<
        T, BaseMovieAndTvRecyclerAdapter.MovieViewHolder>(DiffUtilCallBack<T>()) {


    var itemClickListener: (T) -> Unit = {}
    class MovieViewHolder(
        val binding: MovieRowBinding,
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val context = holder.itemView.context
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

