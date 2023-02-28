package com.e444er.cleanmovie.core.data.repository

import com.e444er.cleanmovie.core.domain.models.FavoriteTvSeries
import com.e444er.cleanmovie.core.domain.models.TvSeriesWatchListItem
import com.e444er.cleanmovie.core.domain.repository.FirebaseCoreTvSeriesRepository
import com.e444er.cleanmovie.core.domain.util.FirebaseFirestoreErrorMessage.Companion.setExceptionToFirebaseMessage
import com.e444er.cleanmovie.core.presentation.util.UiText
import com.e444er.cleanmovie.core.util.Constants
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import javax.inject.Inject

class FirebaseCoreTvSeriesRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : FirebaseCoreTvSeriesRepository {

    override fun addTvSeriesToFavoriteList(
        userUid: String,
        data: Map<String, List<FavoriteTvSeries>>,
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit
    ) {
        firestore.collection(userUid).document(Constants.FIREBASE_FAVORITE_TV_DOCUMENT_NAME)
            .set(data, SetOptions.merge())
            .addOnSuccessListener {
                onSuccess()
            }.addOnFailureListener { exception ->
                setExceptionToFirebaseMessage(exception = exception, onFailure = onFailure)
            }
    }

    override fun addTvSeriesToWatchList(
        userUid: String,
        data: Map<String, List<TvSeriesWatchListItem>>,
        onSuccess: () -> Unit,
        onFailure: (uiText: UiText) -> Unit
    ) {
        firestore.collection(userUid).document(Constants.FIREBASE_TV_WATCH_DOCUMENT_NAME)
            .set(data, SetOptions.merge())
            .addOnSuccessListener {
                onSuccess()
            }.addOnFailureListener { exception ->
                setExceptionToFirebaseMessage(exception = exception, onFailure = onFailure)
            }
    }
}