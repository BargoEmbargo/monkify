package com.application.monkifyapp.screens.onBoarding.viewModel

sealed class OnBoardingEvent {
    object SaveAppEntry: OnBoardingEvent()
}