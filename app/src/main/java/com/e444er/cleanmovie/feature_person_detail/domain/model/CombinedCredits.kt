package com.e444er.cleanmovie.feature_person_detail.domain.model

data class CombinedCredits(
    val cast: List<CastForPerson>,
    val crew: List<CrewForPerson>
)
