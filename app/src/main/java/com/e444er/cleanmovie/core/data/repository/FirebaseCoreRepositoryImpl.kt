package com.e444er.cleanmovie.core.data.repository

import com.e444er.cleanmovie.core.domain.repository.FirebaseCoreRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class FirebaseCoreRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : FirebaseCoreRepository {

    override fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    override fun signOut() {
        auth.signOut()
    }
}