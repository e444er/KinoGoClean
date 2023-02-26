package com.e444er.cleanmovie.core.domain.use_case

import com.e444er.cleanmovie.core.data.dto.Genre
import com.e444er.cleanmovie.core.domain.repository.RemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GetTvGenreListUseCase @Inject constructor(
    private val repository: RemoteRepository
) {

    operator fun invoke(language: String): Flow<List<Genre>> {
        return flow {
            emit(repository.getTvGenreList(language = language.lowercase()).genres)
        }
    }
}