package com.application.monkifyapp.domain.useCases


data class AppEntryUseCases(
    val readAppEntry: ReadAppEntry,
    val saveAppEntry: SaveAppEntry,
    val readDaysCompleted: ReadDaysCompleted,
    val saveDaysCompleted: SaveDaysCompleted
)