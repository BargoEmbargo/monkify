package com.application.monkifyapp.screens.task

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

enum class CategoryTask(val title:String,val icon:ImageVector) {
    Exercise("Exercise", Icons.Default.Warning),
    Stydying("Studying",Icons.Default.ArrowBack),
    Reading("Reading",Icons.Default.ArrowDropDown),
    RidingBike("Riding a Bike",Icons.Default.Check),
    Other("Other",Icons.Default.AccountCircle),
}