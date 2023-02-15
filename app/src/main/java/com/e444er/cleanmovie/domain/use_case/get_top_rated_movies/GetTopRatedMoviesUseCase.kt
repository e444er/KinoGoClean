package com.e444er.cleanmovie.domain.use_case.get_top_rated_movies

import androidx.paging.PagingData
import com.e444er.cleanmovie.domain.models.Movie
import com.e444er.cleanmovie.domain.repository.RemoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTopRatedMoviesUseCase @Inject constructor(
    private val repository: RemoteRepository
) {

    operator fun invoke(
        language: String,
        region: String
    ): Flow<PagingData<Movie>> {
        return repository.getTopRatedMovies(
            language = language.lowercase(),
            region = region
        )
    }
}