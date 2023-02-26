package com.e444er.cleanmovie.feature_person_detail.data.dto

import com.e444er.cleanmovie.feature_person_detail.domain.model.CombinedCredits


data class CombinedCreditsDto(
    val cast: List<CastDtoForPerson>,
    val crew: List<CrewDtoForPerson>
)

fun CombinedCreditsDto.toCombinedCredits(): CombinedCredits {
    return CombinedCredits(
        cast = cast.map { it.toCastForPerson() },
        crew = crew.map { it.toCrewForPerson() }
    )
}