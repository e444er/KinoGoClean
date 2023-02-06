package com.e444er.cleanmovie.domain.use_case

import com.e444er.cleanmovie.domain.use_case.get_movie_genre_list.GetMovieGenreList
import com.e444er.cleanmovie.domain.use_case.get_tv_genre_list.GetTvGenreList
import com.e444er.cleanmovie.domain.use_case.get_locale.GetLocaleUseCase
import com.e444er.cleanmovie.domain.use_case.get_now_playing_movies.GetNowPlayingMoviesUseCase

data class HomeUseCases(
    val getMovieGenreList: GetMovieGenreList,
    val getTvGenreList: GetTvGenreList,
    val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    val getLocaleUseCase: GetLocaleUseCase,
)