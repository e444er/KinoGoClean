package com.e444er.cleanmovie.data.models.detail.credit

import com.e444er.cleanmovie.domain.models.credit.Credit


data class CreditDto(
    val cast: List<CastDto>,
    val crew: List<CrewDto>
)

fun CreditDto.toCredit(): Credit {
    return Credit(
        cast = cast.toCast(),
        crew = crew.toCrew()
    )
}