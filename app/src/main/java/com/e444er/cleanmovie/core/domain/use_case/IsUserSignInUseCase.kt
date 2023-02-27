package com.e444er.cleanmovie.core.domain.use_case

import com.e444er.cleanmovie.core.domain.repository.FirebaseCoreRepository
import javax.inject.Inject

class IsUserSignInUseCase @Inject constructor(
    private val repository: FirebaseCoreRepository
) {
    operator fun invoke(): Boolean {
        return repository.getCurrentUser() != null
    }
}