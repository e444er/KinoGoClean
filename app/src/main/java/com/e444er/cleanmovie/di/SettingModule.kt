package com.e444er.cleanmovie.di

import com.e444er.cleanmovie.core.domain.repository.DataStoreOperations
import com.e444er.cleanmovie.core.domain.use_case.GetLanguageIsoCodeUseCase
import com.e444er.cleanmovie.core.domain.use_case.GetUIModeUseCase
import com.e444er.cleanmovie.core.domain.use_case.UpdateLanguageIsoCodeUseCase
import com.e444er.cleanmovie.core.domain.use_case.UpdateUIModeUseCase
import com.e444er.cleanmovie.feature_settings.domain.use_case.SettingUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SettingModule {

    @Provides
    @Singleton
    fun provideSettingsUseCases(
        dataStoreOperations: DataStoreOperations
    ): SettingUseCase {
        return SettingUseCase(
            getUIModeUseCase = GetUIModeUseCase(dataStoreOperations),
            updateUIModeUseCase = UpdateUIModeUseCase(dataStoreOperations),
            updateLanguageIsoCodeUseCase = UpdateLanguageIsoCodeUseCase(dataStoreOperations),
            getLanguageIsoCodeUseCase = GetLanguageIsoCodeUseCase(dataStoreOperations)
        )
    }
}