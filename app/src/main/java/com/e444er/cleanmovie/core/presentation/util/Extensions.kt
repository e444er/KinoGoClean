package com.e444er.cleanmovie.core.presentation.util

import com.e444er.cleanmovie.core.data.models.enums.*
import com.e444er.cleanmovie.core.util.Constants


fun List<Int>.toSeparateWithComma(): String {

    if (this.isEmpty()) {
        return ""
    }

    if (this.size == 1) {
        return this[0].toString()
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

    if (this.isPopularity()) {
        stringBuilder.append(this.value)
        return stringBuilder.append(".desc").toString()
    }

    if (this.isLatestRelease() && movieCategory.isMovie()) {
        stringBuilder.append(this.value)
    } else {
        stringBuilder.append(Constants.DISCOVER_DATE_QUERY_FOR_TV)
    }

    return stringBuilder.append(".desc").toString()
}