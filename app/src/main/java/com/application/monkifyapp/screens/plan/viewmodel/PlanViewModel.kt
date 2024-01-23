package com.application.monkifyapp.screens.plan.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.monkifyapp.domain.model.ToggleableInfo
import com.application.monkifyapp.domain.useCases.AppEntryUseCases
import com.application.monkifyapp.repository.InfoRepository
import com.application.monkifyapp.screens.onBoarding.viewModel.OnBoardingEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class PlanViewModel @Inject constructor(
    private val infoRepository: InfoRepository,
    private val appEntryUseCases: AppEntryUseCases
    ):ViewModel() {
    private val _infoList = MutableStateFlow<List<ToggleableInfo>>(emptyList())
    val infoList =_infoList.asStateFlow()

    private val _daysCompleted = mutableStateOf(0)
    val daysCompleted: State<Int> get() = _daysCompleted
    init {
        viewModelScope.launch(Dispatchers.IO) {
            infoRepository.getArticles().distinctUntilChanged().collect{ listOfInfo->
                if(listOfInfo.isEmpty()){
                    Log.d("INFO_LIST", "The List Empty: ")
                }else{
                    _infoList.value=listOfInfo
                }
            }
        }
        viewModelScope.launch {
            appEntryUseCases.readDaysCompleted.invoke().collect{
                if (it != null) {
                    _daysCompleted.value=it
                }
            }
        }
    }
    suspend fun upsertInfo(info: ToggleableInfo) = viewModelScope.launch {
        infoRepository.upsertInfo(info)
    }

    // Add this function to update the info list
    fun updateInfoList(newList: List<ToggleableInfo>) {
        _infoList.value = newList
    }

    fun onEvent(event: DaysCompletedEvent){
        when(event){
            is DaysCompletedEvent.SaveDaysCompleted ->{
                saveDaysCompleted(daysCompleted = _daysCompleted.value)
            }
        }
    }
    private fun saveDaysCompleted(daysCompleted:Int) {
        viewModelScope.launch {
            appEntryUseCases.saveDaysCompleted(daysCompleted = daysCompleted)
        }
    }

    fun updateDaysCompleted(newDaysCompleted: Int) {
        _daysCompleted.value = newDaysCompleted
    }
}