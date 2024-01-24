package com.application.monkifyapp.domain.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

enum class CategoryTask(val title:String,val icon:ImageVector) {
    Exercise("Exercise", Icons.Default.SportsMartialArts),
    Stydying("Studying",Icons.Default.LibraryBooks),
    Running("Running",Icons.Default.DirectionsRun),
    Meditating("Meditating",Icons.Default.SelfImprovement),
    SkinCare("Skin-Care",Icons.Default.Face),
    PhoneLocked("Phone Restriction",Icons.Default.PhoneLocked),
    Reading("Reading",Icons.Default.ReadMore),
    RidingBike("Riding a Bike",Icons.Default.PedalBike),
    Other("Other",Icons.Default.MoreHoriz),
}