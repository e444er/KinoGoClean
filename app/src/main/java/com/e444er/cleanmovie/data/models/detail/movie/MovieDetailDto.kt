package com.e444er.cleanmovie.data.models.detail.movie

import com.e444er.cleanmovie.data.models.Genre
import com.e444er.cleanmovie.data.models.detail.ProductionCompany
import com.e444er.cleanmovie.data.models.detail.ProductionCountry
import com.e444er.cleanmovie.data.models.detail.SpokenLanguage
import com.e444er.cleanmovie.data.models.detail.credit.CreditDto
import com.e444er.cleanmovie.data.models.detail.credit.toCredit
import com.e444er.cleanmovie.domain.models.MovieDetail
import com.prmto.mova_movieapp.data.models.watch_provider.WatchProviders
import com.squareup.moshi.Json

data class MovieDetailDto(
    val adult: Boolean,
    @Json(name = "backdrop_path") val backdropPath: String?,
    @Json(name = "belongs_to_collection") val belongsToCollection: Any?,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String?,
    val id: Int,
    @Json(name = "imdb_id") val imdbId: String?,
    @Json(name = "original_language") val originalLanguage: String,
    @Json(name = "original_title") val originalTitle: String,
    val overview: String?,
    val popularity: Double,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "production_companies") val productionCompanies: List<ProductionCompany>,
    @Json(name = "production_countries") val productionCountries: List<ProductionCountry>,
    @Json(name = "release_date") val releaseDate: String,
    val revenue: Int,
    val runtime: Int?,
    @Json(name = "spoken_languages") val spokenLanguages: List<SpokenLanguage>,
    val status: String,
    val tagline: String?,
    val title: String,
    val video: Boolean,
    @Json(name = "vote_average") val voteAverage: Double,
    @Json(name = "vote_count") val voteCount: Int,
    val credits: CreditDto,
    @Json(name = "watch/providers") val watchProviders: WatchProviders
)

fun MovieDetailDto.toMovieDetail(): MovieDetail {
    return MovieDetail(
        id = id,
        genres = genres,
        imdbId = imdbId,
        originalTitle = originalTitle,
        title = title,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        runtime = runtime,
        voteAverage = voteAverage,
        voteCount = voteCount,
        credit = credits.toCredit(),
        watchProviders = watchProviders
    )
}