package com.e444er.cleanmovie.core.domain.use_case

data class LocalDatabaseUseCases(
    val toggleMovieForFavoriteListUseCase: ToggleMovieForFavoriteListUseCase,
    val toggleMovieForWatchListUseCase: ToggleMovieForWatchListUseCase,
    val getFavoriteMovieIdsUseCase: GetFavoriteMovieIdsUseCase,
    val getMovieWatchListItemIdsUseCase: GetMovieWatchListItemIdsUseCase,
    val toggleTvSeriesForFavoriteListUseCase: ToggleTvSeriesForFavoriteListUseCase,
    val toggleTvSeriesForWatchListItemUseCase: ToggleTvSeriesForWatchListItemUseCase,
    val getFavoriteTvSeriesIdsUseCase: GetFavoriteTvSeriesIdsUseCase,
    val getTvSeriesWatchListItemIdsUseCase: GetTvSeriesWatchListItemIdsUseCase
)
