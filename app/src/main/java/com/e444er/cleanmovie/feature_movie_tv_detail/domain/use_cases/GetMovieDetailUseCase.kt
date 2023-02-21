package com.e444er.cleanmovie.feature_movie_tv_detail.domain.use_cases

import com.e444er.cleanmovie.R
import com.e444er.cleanmovie.core.presentation.util.HandleUtils
import com.e444er.cleanmovie.core.presentation.util.UiText
import com.e444er.cleanmovie.core.util.Resource
import com.e444er.cleanmovie.feature_movie_tv_detail.data.dto.detail.movie.toMovieDetail
import com.e444er.cleanmovie.feature_movie_tv_detail.domain.models.detail.MovieDetail
import com.e444er.cleanmovie.feature_movie_tv_detail.domain.repository.DetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val detailRepository: DetailRepository
) {

    operator fun invoke(
        language: String,
        movieId: Int
    ): Flow<Resource<MovieDetail>> {
        return flow {
            try {
                val response =
                    detailRepository.getMovieDetail(language = language, movieId = movieId)
                val result = response.toMovieDetail()

                val movieDetail = result.copy(
                    ratingValue = HandleUtils.calculateRatingBarValue(result.voteAverage),
                    convertedRuntime = HandleUtils.convertRuntimeAsHourAndMinutes(result.runtime)
                )

                emit(Resource.Success(movieDetail))

            } catch (e: IOException) {
                emit(Resource.Error(UiText.StringResource(R.string.internet_error)))
            } catch (e: HttpException) {
                emit(Resource.Error(UiText.StringResource(R.string.oops_something_went_wrong)))
            } catch (e: Exception) {
                Timber.e(e)
                emit(Resource.Error(UiText.StringResource(R.string.oops_something_went_wrong)))
            }
        }
    }
}