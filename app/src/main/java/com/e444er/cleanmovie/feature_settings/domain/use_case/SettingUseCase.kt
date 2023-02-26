package com.e444er.cleanmovie.feature_settings.domain.use_case

import com.e444er.cleanmovie.core.domain.use_case.GetLanguageIsoCodeUseCase
import com.e444er.cleanmovie.core.domain.use_case.GetUIModeUseCase
import com.e444er.cleanmovie.core.domain.use_case.UpdateLanguageIsoCodeUseCase
import com.e444er.cleanmovie.core.domain.use_case.UpdateUIModeUseCase

data class SettingUseCase(
    val getUIModeUseCase: GetUIModeUseCase,
    val updateUIModeUseCase: UpdateUIModeUseCase,
    val updateLanguageIsoCodeUseCase: UpdateLanguageIsoCodeUseCase,
    val getLanguageIsoCodeUseCase: GetLanguageIsoCodeUseCase
)
