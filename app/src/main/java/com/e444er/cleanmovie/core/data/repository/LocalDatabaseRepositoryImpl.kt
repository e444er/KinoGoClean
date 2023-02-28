package com.e444er.cleanmovie.core.data.repository

import com.e444er.cleanmovie.core.data.data_source.local.MovaDatabase
import com.e444er.cleanmovie.core.domain.models.FavoriteMovie
import com.e444er.cleanmovie.core.domain.models.FavoriteTvSeries
import com.e444er.cleanmovie.core.domain.models.MovieWatchListItem
import com.e444er.cleanmovie.core.domain.models.TvSeriesWatchListItem
import com.e444er.cleanmovie.core.domain.repository.LocalDatabaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDatabaseRepositoryImpl @Inject constructor(
    private val database: MovaDatabase
) : LocalDatabaseRepository {

    private val movieDao = database.movieDao
    private val tvSeriesDao = database.tvSeriesDao
    override suspend fun clearDatabase() {
        database.clearAllTables()
    }

    // MOVIES
    override suspend fun insertMovieToFavoriteList(favoriteMovie: FavoriteMovie) {
        movieDao.insertMovieToFavoriteList(
            favoriteMovie = favoriteMovie
        )
    }

    override suspend fun insertMovieToWatchList(movieWatchListItem: MovieWatchListItem) {
        movieDao.insertMovieToWatchListItem(
            movieWatchListItem = movieWatchListItem
        )
    }

    override suspend fun deleteMovieFromFavoriteList(favoriteMovie: FavoriteMovie) {
        movieDao.deleteMovieFromFavoriteList(favoriteMovie = favoriteMovie)
    }

    override suspend fun deleteMovieFromWatchListItem(movieWatchListItem: MovieWatchListItem) {
        movieDao.deleteMovieFromWatchListItem(movieWatchListItem = movieWatchListItem)
    }

    override fun getFavoriteMovieIds(): Flow<List<Int>> {
        return movieDao.getFavoriteMovieIds()
    }

    override fun getMovieWatchListItemIds(): Flow<List<Int>> {
        return movieDao.getMovieWatchListItemIds()
    }

    override fun getFavoriteMovies(): Flow<List<FavoriteMovie>> {
        return movieDao.getFavoriteMovies()
    }

    override fun getMoviesInWatchList(): Flow<List<MovieWatchListItem>> {
        return movieDao.getMovieWatchList()
    }

    // TVSERIES

    override suspend fun insertTvSeriesToFavoriteList(favoriteTvSeries: FavoriteTvSeries) {
        tvSeriesDao.insertTvSeriesToFavoriteList(favoriteTvSeries = favoriteTvSeries)
    }

    override suspend fun insertTvSeriesToWatchListItem(tvSeriesWatchListItem: TvSeriesWatchListItem) {
        tvSeriesDao.insertTvSeriesToWatchListItem(tvSeriesWatchListItem = tvSeriesWatchListItem)
    }

    override suspend fun deleteTvSeriesFromFavoriteList(favoriteTvSeries: FavoriteTvSeries) {
        tvSeriesDao.deleteTvSeriesFromFavoriteList(favoriteTvSeries = favoriteTvSeries)
    }

    override suspend fun deleteTvSeriesFromWatchListItem(tvSeriesWatchListItem: TvSeriesWatchListItem) {
        tvSeriesDao.deleteTvSeriesFromWatchListItem(tvSeriesWatchListItem = tvSeriesWatchListItem)
    }

    override fun getFavoriteTvSeriesIds(): Flow<List<Int>> {
        return tvSeriesDao.getFavoriteTvSeriesIds()
    }

    override fun getTvSeriesWatchListItemIds(): Flow<List<Int>> {
        return tvSeriesDao.getTvSeriesWatchListItemIds()
    }

    override fun getFavoriteTvSeries(): Flow<List<FavoriteTvSeries>> {
        return tvSeriesDao.getFavoriteTvSeries()
    }

    override fun getTvSeriesInWatchList(): Flow<List<TvSeriesWatchListItem>> {
        return tvSeriesDao.getTvSeriesWatchList()
    }
}