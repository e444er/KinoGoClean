package com.e444er.cleanmovie.feature_person_detail.data.repository

import com.e444er.cleanmovie.feature_person_detail.data.dto.toPersonDetail
import com.e444er.cleanmovie.feature_person_detail.data.remote.PersonApi
import com.e444er.cleanmovie.feature_person_detail.domain.model.PersonDetail
import com.e444er.cleanmovie.feature_person_detail.domain.repository.PersonRepository
import javax.inject.Inject

class PersonRepositoryImpl @Inject constructor(
    private val personApi: PersonApi
) : PersonRepository {

    override suspend fun getPersonDetail(personId: Int, language: String): PersonDetail {
        return personApi.getPersonDetail(
            personId = personId,
            language = language
        ).toPersonDetail()
    }
}