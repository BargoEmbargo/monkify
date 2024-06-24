package com.application.monkifyapp.workManager

import android.content.Context
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
   @Assisted val params: WorkerParameters,
   @Assisted val infoRepository: InfoRepository,
   @Assisted val localUserManager: LocalUserManagerImpl,
) : Worker(context, params) {

    override fun doWork(): Result {
        return runBlocking {
            val allTasksCompleted = withContext(Dispatchers.IO) { checkAllTasksCompleted() }
            updateDaysCompleted(allTasksCompleted)
            Result.success()
        }
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