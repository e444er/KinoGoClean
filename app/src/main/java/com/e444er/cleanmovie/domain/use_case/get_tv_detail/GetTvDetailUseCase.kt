package com.e444er.cleanmovie.domain.use_case.get_tv_detail

import com.e444er.cleanmovie.R
import com.e444er.cleanmovie.domain.models.TvDetail
import com.e444er.cleanmovie.domain.repository.RemoteRepository
import com.e444er.cleanmovie.presentation.util.UiText
import com.e444er.cleanmovie.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class GetTvDetailUseCase @Inject constructor(
    private val remoteRepository: RemoteRepository
) {
    operator fun invoke(
        language: String,
        tvId: Int
    ): Flow<Resource<TvDetail>> {
        return flow {
            try {
                val tvDetail = remoteRepository.getTvDetail(language = language, tvId = tvId)
                emit(Resource.Success(data = tvDetail))
            } catch (e: IOException) {
                emit(Resource.Error(UiText.StringResource(R.string.internet_error)))
            } catch (e: HttpException) {
                emit(Resource.Error(UiText.StringResource(R.string.error)))
            }catch (e: Exception) {
                Timber.e(e)
                emit(Resource.Error(UiText.StringResource(R.string.error)))
            }
        }
    }
}