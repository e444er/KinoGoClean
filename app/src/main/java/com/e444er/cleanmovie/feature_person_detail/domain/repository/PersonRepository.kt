package com.e444er.cleanmovie.feature_person_detail.domain.repository

import com.e444er.cleanmovie.feature_person_detail.domain.model.PersonDetail

interface PersonRepository {

    suspend fun getPersonDetail(
        personId: Int,
        language: String
    ): PersonDetail
}