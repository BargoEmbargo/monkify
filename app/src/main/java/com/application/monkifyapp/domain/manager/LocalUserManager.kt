package com.application.monkifyapp.domain.manager

import kotlinx.coroutines.flow.Flow

interface LocalUserManager {
    suspend fun saveAppEntry()

    fun readAppEntry(): Flow<Boolean>

    suspend fun saveDaysCompleted(daysCompleted: Int)

    fun readDaysCompleted(): Flow<Int?>
}