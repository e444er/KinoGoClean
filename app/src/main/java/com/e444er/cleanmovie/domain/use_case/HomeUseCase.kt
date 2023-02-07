package com.e444er.cleanmovie.domain.use_case

import com.e444er.cleanmovie.domain.use_case.get_language_iso_code.GetLanguageIsoCodeUseCase
import com.e444er.cleanmovie.domain.use_case.get_movie_genre_list.GetMovieGenreList
import com.e444er.cleanmovie.domain.use_case.get_now_playing_movies.GetNowPlayingMoviesUseCase
import com.e444er.cleanmovie.domain.use_case.get_popular_movies.GetPopularMoviesUseCase
import com.e444er.cleanmovie.domain.use_case.get_popular_tv_series.GetPopularTvSeries
import com.e444er.cleanmovie.domain.use_case.get_top_rated_movies.GetTopRatedMoviesUseCase
import com.e444er.cleanmovie.domain.use_case.get_top_rated_tv_series.GetTopRatedTvSeriesUseCase
import com.e444er.cleanmovie.domain.use_case.get_tv_genre_list.GetTvGenreList
import com.e444er.cleanmovie.domain.use_case.update_current_language_iso_code.UpdateLanguageIsoCodeUseCase

data class HomeUseCases(
    val getMovieGenreList: GetMovieGenreList,
    val getTvGenreList: GetTvGenreList,
    val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    val getLanguageIsoCodeUseCase: GetLanguageIsoCodeUseCase,
    val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    val getPopularTvSeries: GetPopularTvSeries,
    val getTopRatedTvSeriesUseCase: GetTopRatedTvSeriesUseCase,
    val updateLanguageIsoCodeUseCase: UpdateLanguageIsoCodeUseCase
)