package com.e444er.cleanmovie

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.*
import com.e444er.cleanmovie.core.data.data_source.FirebaseMovieWorker
import com.e444er.cleanmovie.core.data.data_source.FirebaseTvSeriesWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class MovaApp @Inject constructor(
) : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory
    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    }

    override fun onCreate() {
        super.onCreate()

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val firebaseMovieWorker = PeriodicWorkRequestBuilder<FirebaseMovieWorker>(
            repeatInterval = 5, repeatIntervalTimeUnit = TimeUnit.HOURS
        ).setConstraints(constraints).build()

        val firebaseTvSeriesWorker = PeriodicWorkRequestBuilder<FirebaseTvSeriesWorker>(
            repeatInterval = 5, repeatIntervalTimeUnit = TimeUnit.HOURS
        ).setConstraints(constraints).build()

        val workManager = WorkManager.getInstance(this)

        workManager.enqueueUniquePeriodicWork(
            "FirebaseMovieWork",
            ExistingPeriodicWorkPolicy.KEEP,
            firebaseMovieWorker
        )
        workManager.enqueueUniquePeriodicWork(
            "FirebaseTvSeriesWork",
            ExistingPeriodicWorkPolicy.KEEP,
            firebaseTvSeriesWorker
        )
    }


}