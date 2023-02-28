package com.e444er.cleanmovie.core.data.data_source.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.e444er.cleanmovie.core.domain.models.FavoriteTvSeries
import com.e444er.cleanmovie.core.domain.models.TvSeriesWatchListItem
import com.e444er.cleanmovie.core.util.Constants.FAVORITE_TV_SERIES_TABLE_NAME
import com.e444er.cleanmovie.core.util.Constants.TV_SERIES_WATCH_LIST_ITEM_TABLE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface TvSeriesDao {

    @Insert
    suspend fun insertTvSeriesToFavoriteList(favoriteTvSeries: FavoriteTvSeries)

    @Insert
    suspend fun insertTvSeriesToWatchListItem(tvSeriesWatchListItem: TvSeriesWatchListItem)

    @Delete
    suspend fun deleteTvSeriesFromFavoriteList(favoriteTvSeries: FavoriteTvSeries)

    @Delete
    suspend fun deleteTvSeriesFromWatchListItem(tvSeriesWatchListItem: TvSeriesWatchListItem)

    @Query("DELETE FROM $FAVORITE_TV_SERIES_TABLE_NAME")
    suspend fun deleteTvSeriesFavoriteTable()

    @Query("DELETE FROM $TV_SERIES_WATCH_LIST_ITEM_TABLE_NAME")
    suspend fun deleteTvSeriesWatchTable()

    @Query("SELECT tvSeriesId FROM $FAVORITE_TV_SERIES_TABLE_NAME")
    fun getFavoriteTvSeriesIds(): Flow<List<Int>>

    @Query("SELECT tvSeriesId FROM $TV_SERIES_WATCH_LIST_ITEM_TABLE_NAME")
    fun getTvSeriesWatchListItemIds(): Flow<List<Int>>

    @Query("SELECT * FROM $FAVORITE_TV_SERIES_TABLE_NAME")
    fun getFavoriteTvSeries(): Flow<List<FavoriteTvSeries>>

    @Query("SELECT * FROM $TV_SERIES_WATCH_LIST_ITEM_TABLE_NAME")
    fun getTvSeriesWatchList(): Flow<List<TvSeriesWatchListItem>>

}