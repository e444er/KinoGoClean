package com.e444er.cleanmovie.feature_person_detail.data.remote

import com.e444er.cleanmovie.feature_person_detail.data.dto.PersonDetailDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PersonApi {

    @GET("person/{person_id}")
    suspend fun getPersonDetail(
        @Path("person_id") personId: Int,
        @Query("language") language: String,
        @Query("append_to_response") appendToResponse: String = "combined_credits"
    ): PersonDetailDto
}