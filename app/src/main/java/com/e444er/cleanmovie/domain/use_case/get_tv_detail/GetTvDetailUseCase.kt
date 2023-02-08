package com.e444er.cleanmovie.domain.use_case.get_tv_detail

import com.e444er.cleanmovie.R
import com.e444er.cleanmovie.domain.models.TvDetail
import com.e444er.cleanmovie.domain.repository.RemoteRepository
import com.e444er.cleanmovie.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
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
                emit(Resource.Loading())
                val tvDetail = remoteRepository.getTvDetail(language = language, tvId = tvId)
                emit(Resource.Success(data = tvDetail))
            } catch (e: HttpException) {
                emit(Resource.Error(errorRes = R.string.internet_error))
            } catch (e: Exception) {
                emit(Resource.Error(errorRes = R.string.error))
            }
        }
    }
}