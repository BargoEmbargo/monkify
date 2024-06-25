package com.application.monkifyapp.workManager

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.application.monkifyapp.data.manager.LocalUserManagerImpl
import com.application.monkifyapp.repository.InfoRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

@HiltWorker
class TaskCompletionWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    private val notificationBuilder: NotificationCompat.Builder,
    private val notificationManager: NotificationManagerCompat,
    @Assisted private val params: WorkerParameters,
    private val infoRepository: InfoRepository,
    private val localUserManager: LocalUserManagerImpl,
) : Worker(context, params) {

    override fun doWork(): Result {
        return runBlocking {
            val allTasksCompleted = withContext(Dispatchers.IO) { checkAllTasksCompleted() }
            updateDaysCompleted(allTasksCompleted)
            sendNotification(allTasksCompleted)
            Result.success()
        }
    }
    private fun sendNotification(allTasksCompleted: Boolean) {
        val notification = notificationBuilder
            .setContentText(if (allTasksCompleted) "All tasks completed!" else "Not all tasks completed.")
            .build()

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        notificationManager.notify(1, notification)
    }

    private suspend fun checkAllTasksCompleted(): Boolean {
        val tasks = getTasksForToday().first()
        return tasks.all { it.isChecked }
    }

    private suspend fun updateDaysCompleted(allTasksCompleted: Boolean) {
        val daysCompleted = localUserManager.readDaysCompleted().first() ?: 0

        if (allTasksCompleted) {
            localUserManager.saveDaysCompleted(daysCompleted + 1)
        } else {
            localUserManager.saveDaysCompleted(0)
        }
    }

    // Fetch tasks for today using Flow
    private fun getTasksForToday() = infoRepository.getArticles()

}