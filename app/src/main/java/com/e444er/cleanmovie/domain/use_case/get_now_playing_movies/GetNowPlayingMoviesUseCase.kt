package com.e444er.cleanmovie.domain.use_case.get_now_playing_movies

import androidx.paging.PagingData
import com.e444er.cleanmovie.domain.models.Movie
import com.e444er.cleanmovie.domain.repository.RemoteRepository
import com.e444er.cleanmovie.util.Constants.DEFAULT_LANGUAGE
import com.e444er.cleanmovie.util.Constants.DEFAULT_REGION
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNowPlayingMoviesUseCase @Inject constructor(
    private val remoteRepository: RemoteRepository
) {
    operator fun invoke(
        language: String = DEFAULT_LANGUAGE,
        region: String = DEFAULT_REGION
    ): Flow<PagingData<Movie>> {

        return remoteRepository.getNowPlayingMovies(
            language = language.lowercase(),
            region = region
        )
    }
}