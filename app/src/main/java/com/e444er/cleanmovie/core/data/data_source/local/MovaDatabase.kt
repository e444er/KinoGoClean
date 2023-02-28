package com.e444er.cleanmovie.core.data.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.e444er.cleanmovie.core.data.data_source.local.dao.MovieDao
import com.e444er.cleanmovie.core.data.data_source.local.dao.TvSeriesDao
import com.e444er.cleanmovie.core.domain.models.FavoriteMovie
import com.e444er.cleanmovie.core.domain.models.FavoriteTvSeries
import com.e444er.cleanmovie.core.domain.models.MovieWatchListItem
import com.e444er.cleanmovie.core.domain.models.TvSeriesWatchListItem

@Database(
    entities = [FavoriteMovie::class, MovieWatchListItem::class, FavoriteTvSeries::class, TvSeriesWatchListItem::class],
    version = 1
)
@TypeConverters(DatabaseConverter::class)
abstract class MovaDatabase : RoomDatabase() {

    abstract val movieDao: MovieDao

    abstract val tvSeriesDao: TvSeriesDao
}