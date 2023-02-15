package com.e444er.cleanmovie.domain.use_case.get_movie_detail

import com.e444er.cleanmovie.R
import com.e444er.cleanmovie.domain.models.MovieDetail
import com.e444er.cleanmovie.domain.repository.RemoteRepository
import com.e444er.cleanmovie.presentation.util.UiText
import com.e444er.cleanmovie.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val remoteRepository: RemoteRepository
) {
    operator fun invoke(
        language: String,
        movieId: Int
    ): Flow<Resource<MovieDetail>> {
        return flow {
            try {
                val movieDetail =
                    remoteRepository.getMovieDetail(language = language, movieId = movieId)
                emit(Resource.Success(movieDetail))

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