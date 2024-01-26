package com.application.monkifyapp.app

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.monkifyapp.domain.useCases.AppEntryUseCases
import com.application.monkifyapp.navigation.NavigationGraph
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appEntryUseCases: AppEntryUseCases
): ViewModel() {
//    private val _splashCondition = mutableStateOf(true)
//    val splashCondition: State<Boolean> = _splashCondition
//
//    private val _startDestination = mutableStateOf(Route.AppStartNavigation.route)
//    val startDestination: State<String> = _startDestination

    var daysCompleted by mutableStateOf(0)
        private set

    var splashCondition by mutableStateOf(true)
    private set

    var startDestination by mutableStateOf(NavigationGraph.BoardingScreen.name)
    private set
    init {
        appEntryUseCases.readAppEntry().onEach { shouldStartFromHomeScreen ->
            if(shouldStartFromHomeScreen){
                startDestination = NavigationGraph.HomeScreen.name
            }else{
                startDestination = NavigationGraph.BoardingScreen.name
            }
            delay(200) //Without this delay, the onBoarding screen will show for a momentum.
            splashCondition = false
        }.launchIn(viewModelScope)

//        This is for testing purposes only
//        viewModelScope.launch {
//            appEntryUseCases.saveDaysCompleted(0)
//        }
        viewModelScope.launch {
            appEntryUseCases.readDaysCompleted.invoke().collect{
                if (it != null) {
                   daysCompleted=it
                }
            }
        }
    }
}
