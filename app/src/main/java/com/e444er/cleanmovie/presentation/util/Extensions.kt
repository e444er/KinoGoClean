package com.e444er.cleanmovie.presentation.util

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.e444er.cleanmovie.data.models.enums.Categories
import com.e444er.cleanmovie.data.models.enums.Sort
import com.e444er.cleanmovie.util.Constants

fun List<Int>.toSeparateWithComma(): String {

    if (this.isEmpty()) {
        return ""
    }

    val stringBuilder = StringBuilder()

    val separator = ","

    for (item in this) {
        stringBuilder.append(item).append(separator)
    }

    stringBuilder.setLength(stringBuilder.length - separator.length)
    return stringBuilder.toString()
}

fun Sort.toDiscoveryQueryString(movieCategory: Categories): String {

    val stringBuilder = StringBuilder()

    if (this.name == Sort.Popularity.name) {
        stringBuilder.append(this.name)
    }

    if (this.name == Sort.LatestRelease.name && movieCategory == Categories.MOVIE) {
        stringBuilder.append(this.name)
    } else {
        stringBuilder.append(Constants.DISCOVER_DATE_QUERY_FOR_TV)
    }

    return stringBuilder.append(".desc").toString()
}

fun CombinedLoadStates.isErrorWithLoadState(): LoadState.Error? {
    return when {
        this.refresh is LoadState.Error -> this.refresh as LoadState.Error
        this.append is LoadState.Error -> this.append as LoadState.Error
        this.prepend is LoadState.Error -> this.prepend as LoadState.Error
        else -> null
    }
}

fun CombinedLoadStates.isLoading(): Boolean {

    return when (this.refresh) {
        is LoadState.Loading -> true
        is LoadState.NotLoading -> false
        else -> false
    }
}