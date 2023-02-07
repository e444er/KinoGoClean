package com.e444er.cleanmovie.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.e444er.cleanmovie.domain.repository.DataStoreOperations
import com.e444er.cleanmovie.util.Constants
import com.e444er.cleanmovie.util.Constants.LOCALE_KEY
import com.e444er.cleanmovie.util.Constants.PREFERENCES_NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class DataOperationsImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : DataStoreOperations {

    private object PreferencesKey {
        val localeKey = stringPreferencesKey(LOCALE_KEY)
    }

    override suspend fun updateCurrentLocale(locale: String) {
        dataStore.edit {
            it[PreferencesKey.localeKey] = locale
        }
    }

    override fun getLocale(): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }

            }.map {
                val locale = it[PreferencesKey.localeKey] ?: Constants.DEFAULT_REGION
                locale
            }
    }

}