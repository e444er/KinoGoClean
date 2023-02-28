package com.e444er.cleanmovie

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.*
import com.e444er.cleanmovie.core.data.data_source.worker.UpdateFirebaseMovieWorker
import com.e444er.cleanmovie.core.data.data_source.worker.UpdateFirebaseTvSeriesWorker
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class MovaApp @Inject constructor(
) : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val updateFirebaseMovieWorker = PeriodicWorkRequestBuilder<UpdateFirebaseMovieWorker>(
            repeatInterval = 1, repeatIntervalTimeUnit = TimeUnit.DAYS
        ).setConstraints(constraints).build()

        val updateFirebaseTvSeriesWorker = PeriodicWorkRequestBuilder<UpdateFirebaseTvSeriesWorker>(
            repeatInterval = 1, repeatIntervalTimeUnit = TimeUnit.DAYS
        ).setConstraints(constraints).build()

        val workManager = WorkManager.getInstance(this)

        workManager.enqueueUniquePeriodicWork(
            "FirebaseMovieWork",
            ExistingPeriodicWorkPolicy.KEEP,
            updateFirebaseMovieWorker
        )
        workManager.enqueueUniquePeriodicWork(
            "FirebaseTvSeriesWork",
            ExistingPeriodicWorkPolicy.KEEP,
            updateFirebaseTvSeriesWorker
        )
    }
}