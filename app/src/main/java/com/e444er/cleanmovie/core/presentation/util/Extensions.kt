package com.e444er.cleanmovie.core.presentation.util

import com.e444er.cleanmovie.core.data.models.enums.Category
import com.e444er.cleanmovie.core.data.models.enums.Sort
import com.e444er.cleanmovie.core.util.Constants


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

fun Sort.toDiscoveryQueryString(movieCategory: Category): String {

    val stringBuilder = StringBuilder()

    if (this.name == Sort.Popularity.name) {
        stringBuilder.append(this.name)
    }

    if (this.name == Sort.LatestRelease.name && movieCategory == Category.MOVIE) {
        stringBuilder.append(this.name)
    } else {
        stringBuilder.append(Constants.DISCOVER_DATE_QUERY_FOR_TV)
    }

    return stringBuilder.append(".desc").toString()
}