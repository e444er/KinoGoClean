package com.e444er.cleanmovie.feature_movie_tv_detail.domain.use_cases

import com.e444er.cleanmovie.R
import com.e444er.cleanmovie.core.presentation.util.UiText
import com.e444er.cleanmovie.core.util.Resource
import com.e444er.cleanmovie.feature_movie_tv_detail.data.dto.detail.video.toVideo
import com.e444er.cleanmovie.feature_movie_tv_detail.domain.models.detail.video.Videos
import com.e444er.cleanmovie.feature_movie_tv_detail.domain.repository.DetailRepository
import com.e444er.cleanmovie.feature_movie_tv_detail.presentation.util.Constants
import okio.IOException
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

class GetTvVideosUseCase @Inject constructor(
    private val repository: DetailRepository
) {
    suspend operator fun invoke(tvId: Int, language: String): Resource<Videos> {
        return try {
            val response = repository.getTvVideos(
                tvId = tvId,
                language = language.lowercase()
            )
            val tvVideos = response.toVideo()

            val result = tvVideos.copy(
                result = tvVideos.result.reversed()
                    .sortedByDescending { it.type == Constants.TYPE_TRAILER }
            )

            Resource.Success(data = result)
        } catch (e: IOException) {
            Resource.Error(uiText = UiText.StringResource(R.string.internet_error))
        } catch (e: HttpException) {
            Timber.e(e.message())
            Resource.Error(UiText.StringResource(R.string.error))
        } catch (e: Exception) {
            Timber.e(e.localizedMessage?.toString())
            Resource.Error(UiText.StringResource(R.string.error))
        }
    }
}