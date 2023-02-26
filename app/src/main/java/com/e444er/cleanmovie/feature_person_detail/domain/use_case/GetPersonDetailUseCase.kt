package com.e444er.cleanmovie.feature_person_detail.domain.use_case

import com.e444er.cleanmovie.R
import com.e444er.cleanmovie.core.presentation.util.UiText
import com.e444er.cleanmovie.core.util.Resource
import com.e444er.cleanmovie.feature_person_detail.domain.DateFormatUtils
import com.e444er.cleanmovie.feature_person_detail.domain.model.PersonDetail
import com.e444er.cleanmovie.feature_person_detail.domain.repository.PersonRepository
import okio.IOException
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

class GetPersonDetailUseCase @Inject constructor(
    private val repository: PersonRepository
) {

    suspend operator fun invoke(
        personId: Int,
        language: String
    ): Resource<PersonDetail> {
        return try {
            val result = repository.getPersonDetail(personId = personId, language = language)
            Resource.Success(
                data = result.copy(
                    birthday = DateFormatUtils.convertDateFormat(inputDate = result.birthday),
                    deathday = if (result.deathday != null) DateFormatUtils.convertDateFormat(
                        inputDate = result.deathday
                    ) else null
                )
            )
        } catch (e: IOException) {
            Resource.Error(UiText.StringResource(R.string.internet_error))
        } catch (e: HttpException) {
            Resource.Error(UiText.StringResource(R.string.oops_something_went_wrong))
        } catch (e: Exception) {
            Timber.e(e)
            Resource.Error(UiText.StringResource(R.string.oops_something_went_wrong))
        }
    }
}