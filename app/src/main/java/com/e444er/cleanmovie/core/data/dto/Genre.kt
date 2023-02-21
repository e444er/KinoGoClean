package com.e444er.cleanmovie.core.data.dto

data class Genre(
    val id: Int,
    val name: String
)

data class GenreList(
    val genres: List<Genre>
)
