package com.e444er.cleanmovie.presentation.util

import com.e444er.cleanmovie.domain.models.Genre
import com.e444er.cleanmovie.domain.models.Movie

object Util {
    fun handleReleaseDate(releaseDate: String): String {
        return releaseDate.split("-")[0]
    }


    fun handleGenreText(movieGenreList: List<Genre>, movie: Movie): String {
        var genreNames = ""
        movieGenreList.forEach { genre ->
            movie.genreIds.forEach {
                if (it == genre.id) {
                    genreNames += "${genre.name}, "
                }
            }
        }

        return genreNames
    }

    fun handleGenreOneText(movieGenreList: List<Genre>, genreIds: List<Int>): String {

        for (genre: Genre in movieGenreList) {
            for (genreId: Int in genreIds) {
                if (genreId == genre.id) {
                    return genre.name
                }
                break

            }
        }

        return ""

    }

    fun handleVoteCount(voteCount: Int): String {
        if (voteCount < 1000)
            return voteCount.toString()

        val voteCountText: Int?

        val divide = (voteCount / 1000).toFloat()

        val mod = voteCount % 1000

        if (mod > 100) {
            voteCountText = (mod / 100)
        } else {
            return "${divide.toInt()} k"
        }

        return "${divide.toInt()}.$voteCountText k"
    }
}