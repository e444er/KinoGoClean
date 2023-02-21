package com.e444er.cleanmovie.feature_home.data_source.remote

import com.e444er.cleanmovie.core.data.dto.ApiResponse
import com.e444er.cleanmovie.core.util.Constants
import com.e444er.cleanmovie.feature_home.data.dto.MovieDto
import com.e444er.cleanmovie.feature_home.data.dto.TvSeriesDto
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApi {

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int,
        @Query("region") region: String,
        @Query("language") language: String
    ): ApiResponse<MovieDto>

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int = Constants.STARTING_PAGE,
        @Query("region") region: String,
        @Query("language") language: String
    ): ApiResponse<MovieDto>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int = Constants.STARTING_PAGE,
        @Query("region") region: String,
        @Query("language") language: String
    ): ApiResponse<MovieDto>


    @GET("tv/popular")
    suspend fun getPopularTvs(
        @Query("page") page: Int = Constants.STARTING_PAGE,
        @Query("language") language: String,
    ): ApiResponse<TvSeriesDto>

    @GET("tv/top_rated")
    suspend fun getTopRatedTvs(
        @Query("page") page: Int = Constants.STARTING_PAGE,
        @Query("language") language: String,
    ): ApiResponse<TvSeriesDto>
}