package com.application.monkifyapp.screens.plan.viewmodel

sealed class DaysCompletedEvent() {
    object SaveDaysCompleted: DaysCompletedEvent()
}