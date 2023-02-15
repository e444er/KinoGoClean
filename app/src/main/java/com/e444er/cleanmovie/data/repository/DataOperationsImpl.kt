package com.e444er.cleanmovie.data.repository

import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.e444er.cleanmovie.domain.repository.DataStoreOperations
import com.e444er.cleanmovie.util.Constants
import com.e444er.cleanmovie.util.Constants.COUNTRY_KEY
import com.e444er.cleanmovie.util.Constants.LOCALE_KEY
import com.e444er.cleanmovie.util.Constants.UI_MODE_KEY
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
        val uiModeKey = intPreferencesKey(UI_MODE_KEY)
        val countryCodeKey = stringPreferencesKey(COUNTRY_KEY)
    }


    override suspend fun updateCurrentLanguageIsoCode(languageTag: String) {
        dataStore.edit {
            it[PreferencesKey.localeKey] = languageTag
        }
    }

    override fun getLanguageIsoCode(): Flow<String> {
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

//    override fun getUserCountryIsoCode(): Flow<String> {
//        return dataStore.data
//            .catch { exception ->
//                if (exception is IOException) {
//                    emit(emptyPreferences())
//                } else {
//                    throw exception
//                }
//            }.map {
//                val userCountryCode = it[PreferencesKey.countryCodeKey] ?: ""
//                userCountryCode
//            }
//    }
//
//    override suspend fun updateUserCountryIsoCode(countryIsoCode: String) {
//        dataStore.edit {
//            it[PreferencesKey.countryCodeKey] = countryIsoCode
//        }
//    }

    override suspend fun updateUIMode(uiMode: Int) {
        dataStore.edit {
            it[PreferencesKey.uiModeKey] = uiMode
        }
    }

    override fun getUIMode(): Flow<Int> {
        return dataStore.data
            .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map {
            val uiMode = it[PreferencesKey.uiModeKey] ?: AppCompatDelegate.getDefaultNightMode()
            uiMode
        }
    }

}