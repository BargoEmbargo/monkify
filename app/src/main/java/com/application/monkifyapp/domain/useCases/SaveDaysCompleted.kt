package com.application.monkifyapp.domain.useCases

import com.application.monkifyapp.domain.manager.LocalUserManager

class SaveDaysCompleted(private val localUserManager: LocalUserManager) {
    suspend operator fun invoke(daysCompleted:Int){
        localUserManager.saveDaysCompleted(daysCompleted)
    }
}