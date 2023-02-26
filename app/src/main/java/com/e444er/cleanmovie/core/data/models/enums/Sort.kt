package com.e444er.cleanmovie.core.data.models.enums

enum class Sort(val value: String) {
    Popularity(value = "popularity"),
    LatestRelease(value = "release_date")
}

fun Sort.isPopularity(): Boolean {
    return this.value == Sort.Popularity.value
}

fun Sort.isLatestRelease(): Boolean {
    return this.value == Sort.LatestRelease.value
}