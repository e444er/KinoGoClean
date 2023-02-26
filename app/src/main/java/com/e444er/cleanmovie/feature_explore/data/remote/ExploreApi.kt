package com.e444er.cleanmovie.feature_explore.data.remote

import com.e444er.cleanmovie.core.data.dto.ApiResponse
import com.e444er.cleanmovie.core.util.Constants
import com.e444er.cleanmovie.feature_explore.data.dto.SearchDto
import com.e444er.cleanmovie.feature_home.data.dto.MovieDto
import com.e444er.cleanmovie.feature_home.data.dto.TvSeriesDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ExploreApi {
    @GET("discover/movie")
    suspend fun discoverMovie(
        @Query("page") page: Int = Constants.STARTING_PAGE,
        @Query("language") language: String,
        @Query("with_genres") genres: String = "",
        @Query("sort_by") sort: String
    ): ApiResponse<MovieDto>


    @GET("discover/tv")
    suspend fun discoverTv(
        @Query("page") page: Int = Constants.STARTING_PAGE,
        @Query("language") language: String,
        @Query("with_genres") genres: String = "",
        @Query("sort_by") sort: String
    ): ApiResponse<TvSeriesDto>


    @GET("search/multi")
    suspend fun multiSearch(
        @Query("query") query: String,
        @Query("language") language: String,
        @Query("page") page: Int = Constants.STARTING_PAGE
    ): ApiResponse<SearchDto>
}