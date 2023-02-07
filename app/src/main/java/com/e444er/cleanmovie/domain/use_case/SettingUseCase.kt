package com.e444er.cleanmovie.domain.use_case

import com.e444er.cleanmovie.domain.use_case.get_language_iso_code.GetLanguageIsoCodeUseCase
import com.e444er.cleanmovie.domain.use_case.get_ui_mode.GetUIModeUseCase
import com.e444er.cleanmovie.domain.use_case.update_current_language_iso_code.UpdateLanguageIsoCodeUseCase
import com.e444er.cleanmovie.domain.use_case.update_ui_mode.UpdateUIModeUseCase

data class SettingUseCase(
    val getUIModeUseCase: GetUIModeUseCase,
    val updateUIModeUseCase: UpdateUIModeUseCase,
    val updateLanguageIsoCodeUseCase: UpdateLanguageIsoCodeUseCase,
    val getLanguageIsoCodeUseCase: GetLanguageIsoCodeUseCase
)
