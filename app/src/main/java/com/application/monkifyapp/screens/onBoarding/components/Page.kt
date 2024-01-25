package com.application.monkifyapp.screens.onBoarding.components

import androidx.annotation.DrawableRes
import com.application.monkifyapp.R

data class Page(
    val title: String,
    @DrawableRes val backgroundImage : Int,
    @DrawableRes val pageImage : Int,
    val description: String
    )

val onBoardingPages= listOf(

    Page("Unlock Your Potential!",
        R.drawable.background,
        R.drawable.page_one,
        "Consistency is the key to achieving your goals. " +
                "Welcome to Monkify, your daily companion on the journey to a more disciplined and successful you. " +
                "Let's get started!"
    ),

    Page("Define Your Path",
        R.drawable.background,
        R.drawable.page_two,
        "Take control of your life by setting clear, achievable goals." +
                " Monkify helps you define your path and holds you accountable, " +
                "ensuring you stay on track to turn your dreams into reality."
    ),

    Page("Cultivate Lasting Habits",
        R.drawable.background,
        R.drawable.page_three,
        "Success isn't a one-time event; it's the result of daily habits. " +
                "With Monkify, you can build a foundation of discipline that leads to lifelong success. " +
                "Start your journey today."
    ),
)
