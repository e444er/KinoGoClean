package com.e444er.cleanmovie.core.domain.use_case

import com.e444er.cleanmovie.core.domain.repository.FirebaseCoreRepository
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val repository: FirebaseCoreRepository
) {
    operator fun invoke(): FirebaseUser? {
        return repository.getCurrentUser()
    }
}