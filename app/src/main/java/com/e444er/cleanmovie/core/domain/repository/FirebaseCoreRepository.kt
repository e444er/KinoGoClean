package com.e444er.cleanmovie.core.domain.repository

import com.google.firebase.auth.FirebaseUser

interface FirebaseCoreRepository {

    fun getCurrentUser(): FirebaseUser?

    fun signOut()
}