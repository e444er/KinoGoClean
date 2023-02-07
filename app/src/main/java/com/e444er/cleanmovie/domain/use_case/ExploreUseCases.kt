package com.e444er.cleanmovie.domain.use_case

import com.e444er.cleanmovie.domain.use_case.discover_movie.DiscoverMovieUseCase
import com.e444er.cleanmovie.domain.use_case.discover_tv.DiscoverTvUseCase
import com.e444er.cleanmovie.domain.use_case.get_language_iso_code.GetLanguageIsoCodeUseCase
import com.e444er.cleanmovie.domain.use_case.get_movie_genre_list.GetMovieGenreList
import com.e444er.cleanmovie.domain.use_case.get_tv_genre_list.GetTvGenreList

data class ExploreUseCases(
    val tvGenreListUseCase: GetTvGenreList,
    val movieGenreListUseCase: GetMovieGenreList,
    val getLanguageIsoCodeUseCase: GetLanguageIsoCodeUseCase,
    val discoverTvUseCase: DiscoverTvUseCase,
    val discoverMovieUseCase: DiscoverMovieUseCase
)