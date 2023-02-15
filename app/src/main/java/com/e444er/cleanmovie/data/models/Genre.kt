package com.e444er.cleanmovie.data.models


data class Genre(
    val id: Int,
    val name: String
)

fun Genre.toGenre(): Genre {
    return Genre(
        id, name
    )
}

data class GenreList(
    val genres: List<Genre>
)
