package com.e444er.cleanmovie.core.domain.repository

import com.e444er.cleanmovie.core.domain.models.FavoriteMovie
import com.e444er.cleanmovie.core.domain.models.FavoriteTvSeries
import com.e444er.cleanmovie.core.domain.models.MovieWatchListItem
import com.e444er.cleanmovie.core.domain.models.TvSeriesWatchListItem
import kotlinx.coroutines.flow.Flow

interface LocalDatabaseRepository {

    // MOVIES
    suspend fun insertMovieToFavoriteList(favoriteMovie: FavoriteMovie)

    suspend fun insertMovieToWatchList(movieWatchListItem: MovieWatchListItem)

    suspend fun deleteMovieFromFavoriteList(favoriteMovie: FavoriteMovie)

    suspend fun deleteMovieFromWatchListItem(movieWatchListItem: MovieWatchListItem)

    fun getFavoriteMovieIds(): Flow<List<Int>>

    fun getMovieWatchListItemIds(): Flow<List<Int>>

    fun getFavoriteMovies(): Flow<List<FavoriteMovie>>

    fun getMoviesInWatchList(): Flow<List<MovieWatchListItem>>

    suspend fun deleteMovieFavoriteTable()
    suspend fun deleteMovieWatchTable()

    // TVSERIES
    suspend fun insertTvSeriesToFavoriteList(favoriteTvSeries: FavoriteTvSeries)

    suspend fun insertTvSeriesToWatchListItem(tvSeriesWatchListItem: TvSeriesWatchListItem)

    suspend fun deleteTvSeriesFromFavoriteList(favoriteTvSeries: FavoriteTvSeries)

    suspend fun deleteTvSeriesFromWatchListItem(tvSeriesWatchListItem: TvSeriesWatchListItem)

    fun getFavoriteTvSeriesIds(): Flow<List<Int>>

    fun getTvSeriesWatchListItemIds(): Flow<List<Int>>

    fun getFavoriteTvSeries(): Flow<List<FavoriteTvSeries>>

    fun getTvSeriesInWatchList(): Flow<List<TvSeriesWatchListItem>>

    suspend fun deleteTvSeriesFavoriteTable()

    suspend fun deleteTvSeriesWatchTable()

}