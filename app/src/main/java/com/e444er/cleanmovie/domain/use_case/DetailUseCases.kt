package com.e444er.cleanmovie.domain.use_case

import com.e444er.cleanmovie.domain.use_case.get_movie_detail.GetMovieDetailUseCase
import com.e444er.cleanmovie.domain.use_case.get_tv_detail.GetTvDetailUseCase

data class DetailUseCases(
    val movieDetailUseCase: GetMovieDetailUseCase,
    val tvDetailUseCase: GetTvDetailUseCase
)
