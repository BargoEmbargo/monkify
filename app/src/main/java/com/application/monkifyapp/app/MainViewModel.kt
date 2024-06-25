package com.application.monkifyapp.app

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.application.monkifyapp.domain.useCases.AppEntryUseCases
import com.application.monkifyapp.navigation.NavigationGraph
import com.application.monkifyapp.workManager.TaskCompletionWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appEntryUseCases: AppEntryUseCases,
    private val workManager: WorkManager,
    private val application: Application // Inject the Application context
) : ViewModel() {

    var daysCompleted by mutableStateOf(0)
        private set

    var splashCondition by mutableStateOf(true)
        private set

    var startDestination by mutableStateOf(NavigationGraph.BoardingScreen.name)
        private set



    init {
        initAppEntryUseCases()
        scheduleTaskCompletionWorker()
    }

    private fun initAppEntryUseCases() {
        viewModelScope.launch {
            appEntryUseCases.readAppEntry().onEach { shouldStartFromHomeScreen ->
                startDestination = if (shouldStartFromHomeScreen) {
                    NavigationGraph.HomeScreen.name
                } else {
                    NavigationGraph.BoardingScreen.name
                }
                delay(200)
                splashCondition = false
            }.launchIn(viewModelScope)
        }

        viewModelScope.launch {
            appEntryUseCases.readDaysCompleted().collect { days ->
                days?.let {
                    daysCompleted = it
                }
            }
        }
    }
    //workmanager
//    private fun scheduleTaskCompletionWorker() {
//        val repeatInterval = 15L // Interval in minutes
//        val repeatIntervalTimeUnit = TimeUnit.MINUTES
//
//        val workRequest = PeriodicWorkRequestBuilder<TaskCompletionWorker>(
//            repeatInterval, // Repeat interval in minutes
//            repeatIntervalTimeUnit
//        ).setInitialDelay(15,TimeUnit.MINUTES).build()
//        workManager.enqueueUniquePeriodicWork(
//            "TaskCompletionWork",
//            ExistingPeriodicWorkPolicy.REPLACE,
//            workRequest
//        )
//    }

    //testing
    private fun scheduleTaskCompletionWorker() {
        val workRequest = OneTimeWorkRequestBuilder<TaskCompletionWorker>()
            .setInitialDelay(1, TimeUnit.MINUTES)
            .build()

        workManager.enqueueUniqueWork(
            "TaskCompletionWork",
            ExistingWorkPolicy.REPLACE,
            workRequest
        )
    }
}
