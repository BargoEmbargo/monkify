package com.application.monkifyapp.domain.useCases

import com.application.monkifyapp.domain.manager.LocalUserManager
import kotlinx.coroutines.flow.Flow

class ReadAppEntry(
    private val localUserManager:LocalUserManager
) {

     operator fun invoke(): Flow<Boolean> {
        return localUserManager.readAppEntry()
    }
}