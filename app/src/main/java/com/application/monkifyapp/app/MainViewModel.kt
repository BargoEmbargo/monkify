package com.application.monkifyapp.app

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
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
    private val application: Application // Inject the Application context
) : ViewModel() {

    var daysCompleted by mutableStateOf(0)
        private set

    var splashCondition by mutableStateOf(true)
        private set

    var startDestination by mutableStateOf(NavigationGraph.BoardingScreen.name)
        private set

    private val workManager = WorkManager.getInstance(application)

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

    private fun scheduleTaskCompletionWorker() {
        val repeatInterval = 1L // Interval in minutes
        val repeatIntervalTimeUnit = TimeUnit.MINUTES

        val workRequest = PeriodicWorkRequestBuilder<TaskCompletionWorker>(
            repeatInterval, // Repeat interval in minutes
            repeatIntervalTimeUnit
        ).build()
        workManager.enqueue(workRequest)
    }
}
