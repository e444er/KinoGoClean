package com.e444er.cleanmovie.core.data.data_source.remote

import com.e444er.cleanmovie.core.data.dto.GenreList
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBApi {

    @GET("genre/movie/list")
    suspend fun getMovieGenreList(
        @Query("language") language: String
    ): GenreList

    @GET("genre/tv/list")
    suspend fun getTvGenreList(
        @Query("language") language: String
    ): GenreList
}