package com.e444er.cleanmovie.feature_person_detail.domain.use_case

import com.e444er.cleanmovie.core.domain.use_case.GetLanguageIsoCodeUseCase


data class PersonDetailUseCases(
    val getPersonDetailUseCase: GetPersonDetailUseCase,
    val getLanguageIsoCodeUseCase: GetLanguageIsoCodeUseCase
)
