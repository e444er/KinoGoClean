package com.e444er.cleanmovie.domain.use_case

import com.e444er.cleanmovie.domain.use_case.get_movie_genre_list.GetMovieGenreList
import com.e444er.cleanmovie.domain.use_case.get_tv_genre_list.GetTvGenreList
import com.e444er.cleanmovie.domain.use_case.get_locale.GetLocaleUseCase
import com.e444er.cleanmovie.domain.use_case.get_now_playing_movies.GetNowPlayingMoviesUseCase
import com.e444er.cleanmovie.domain.use_case.get_popular_movies.GetPopularMoviesUseCase
import com.e444er.cleanmovie.domain.use_case.get_popular_tv_series.GetPopularTvSeries
import com.e444er.cleanmovie.domain.use_case.get_top_rated_movies.GetTopRatedMoviesUseCase

data class HomeUseCases(
    val getMovieGenreList: GetMovieGenreList,
    val getTvGenreList: GetTvGenreList,
    val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    val getLocaleUseCase: GetLocaleUseCase,
    val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    val getPopularTvSeries: GetPopularTvSeries
)