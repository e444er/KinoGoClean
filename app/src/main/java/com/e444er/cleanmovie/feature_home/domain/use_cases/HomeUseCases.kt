package com.e444er.cleanmovie.feature_home.domain.use_cases

import com.e444er.cleanmovie.core.domain.use_case.GetLanguageIsoCodeUseCase
import com.e444er.cleanmovie.core.domain.use_case.GetMovieGenreListUseCase
import com.e444er.cleanmovie.core.domain.use_case.GetTvGenreListUseCase
import com.e444er.cleanmovie.core.domain.use_case.UpdateLanguageIsoCodeUseCase

data class HomeUseCases(
    val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    val getLanguageIsoCodeUseCase: GetLanguageIsoCodeUseCase,
    val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    val getPopularTvSeriesUseCase: GetPopularTvSeriesUseCase,
    val getTopRatedTvSeriesUseCase: GetTopRatedTvSeriesUseCase,
    val updateLanguageIsoCodeUseCase: UpdateLanguageIsoCodeUseCase
)
