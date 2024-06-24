package com.application.monkifyapp.app

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.work.Configuration
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.application.monkifyapp.data.manager.LocalUserManagerImpl
import com.application.monkifyapp.domain.manager.LocalUserManager
import com.application.monkifyapp.repository.InfoRepository
import com.application.monkifyapp.workManager.TaskCompletionWorker
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory:CustomWorkerFactory
    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setMinimumLoggingLevel(Log.DEBUG)
            .setWorkerFactory(workerFactory)
            .build()

}
class CustomWorkerFactory @Inject constructor(
    private val infoRepository: InfoRepository,
    private val localUserManager: LocalUserManager)
    : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? = TaskCompletionWorker(
        infoRepository = infoRepository,
        localUserManager = localUserManager as LocalUserManagerImpl,
        context = appContext,
        params = workerParameters
    )

}