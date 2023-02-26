package com.e444er.cleanmovie.feature_explore.domain.use_case

import com.e444er.cleanmovie.core.domain.use_case.GetLanguageIsoCodeUseCase
import com.e444er.cleanmovie.core.domain.use_case.GetMovieGenreListUseCase
import com.e444er.cleanmovie.core.domain.use_case.GetTvGenreListUseCase

data class ExploreUseCases(
    val tvGenreListUseCase: GetTvGenreListUseCase,
    val movieGenreListUseCase: GetMovieGenreListUseCase,
    val getLanguageIsoCodeUseCase: GetLanguageIsoCodeUseCase,
    val discoverTvUseCase: DiscoverTvUseCase,
    val discoverMovieUseCase: DiscoverMovieUseCase,
    val multiSearchUseCase: MultiSearchUseCase
)