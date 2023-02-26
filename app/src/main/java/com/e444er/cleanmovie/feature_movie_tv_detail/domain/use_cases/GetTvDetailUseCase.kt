package com.e444er.cleanmovie.feature_movie_tv_detail.domain.use_cases

import com.e444er.cleanmovie.R
import com.e444er.cleanmovie.core.presentation.util.UiText
import com.e444er.cleanmovie.core.util.Resource
import com.e444er.cleanmovie.feature_movie_tv_detail.data.dto.detail.tv.toTvDetail
import com.e444er.cleanmovie.feature_movie_tv_detail.domain.models.detail.TvDetail
import com.e444er.cleanmovie.feature_movie_tv_detail.domain.repository.DetailRepository
import com.e444er.cleanmovie.feature_movie_tv_detail.domain.util.HandleUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

class GetTvDetailUseCase @Inject constructor(
    private val detailRepository: DetailRepository
) {

    operator fun invoke(
        language: String,
        tvId: Int
    ): Flow<Resource<TvDetail>> {
        return flow {
            try {
                val response = detailRepository.getTvDetail(language = language, tvId = tvId)
                val result = response.toTvDetail()
                val tvDetail = result.copy(
                    ratingValue = HandleUtils.calculateRatingBarValue(result.voteAverage),
                    releaseDate = HandleUtils.convertTvSeriesReleaseDateBetweenFirstAndLastDate(
                        firstAirDate = result.firstAirDate,
                        lastAirDate = result.lastAirDate,
                        status = result.status
                    )
                )

                emit(Resource.Success(data = tvDetail))
            } catch (e: IOException) {
                emit(Resource.Error(UiText.StringResource(R.string.internet_error)))
            } catch (e: HttpException) {
                emit(Resource.Error(UiText.StringResource(R.string.error)))
            } catch (e: Exception) {
                Timber.e(e)
                emit(Resource.Error(UiText.StringResource(R.string.error)))
            }
        }
    }
}