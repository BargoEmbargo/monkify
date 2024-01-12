package com.application.monkifyapp.screens.task

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.monkifyapp.domain.model.ToggleableInfo
import com.application.monkifyapp.repository.InfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(private val infoRepository: InfoRepository):ViewModel() {
    private val _infoList =MutableStateFlow<List<ToggleableInfo>>(emptyList())
    val infoList =_infoList.asStateFlow()
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
     }
    suspend fun upsertInfo(info:ToggleableInfo) = viewModelScope.launch {
        infoRepository.upsertInfo(info)
    }
    suspend fun deleteInfo(info: ToggleableInfo)=viewModelScope.launch {
        infoRepository.deleteInfo(info)
    }

    // Add this function to update the info list
    fun updateInfoList(newList: List<ToggleableInfo>) {
        _infoList.value = newList
    }

}