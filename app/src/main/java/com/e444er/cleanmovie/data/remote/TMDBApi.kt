package com.e444er.cleanmovie.data.remote

import com.e444er.cleanmovie.data.models.ApiResponse
import com.e444er.cleanmovie.data.models.MovieDto
import com.e444er.cleanmovie.data.models.TvSeriesDto
import com.e444er.cleanmovie.domain.models.Genre
import com.e444er.cleanmovie.domain.models.GenreList
import com.e444er.cleanmovie.util.Constants.API_KEY
import com.e444er.cleanmovie.util.Constants.STARTING_PAGE
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBApi {

    @GET("genre/movie/list")
    suspend fun getMovieGenreList(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String
    ): GenreList

    @GET("genre/tv/loist")
    suspend fun getTvGenreList(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String
    ): GenreList

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int = STARTING_PAGE,
        @Query("region") region: String,
        @Query("language") language: String,
    ): ApiResponse<MovieDto>

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int = STARTING_PAGE,
        @Query("language") language: String,
        @Query("region") region: String
    ): ApiResponse<MovieDto>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int = STARTING_PAGE,
        @Query("language") language: String,
        @Query("region") region: String
    ): ApiResponse<MovieDto>


    @GET("tv/popular")
    suspend fun getPopularTvs(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int = STARTING_PAGE,
        @Query("language") language: String,
    ): ApiResponse<TvSeriesDto>

    @GET("tv/top_rated")
    suspend fun getTopRatedTvs(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int = STARTING_PAGE,
        @Query("language") language: String,
    ): ApiResponse<TvSeriesDto>
}