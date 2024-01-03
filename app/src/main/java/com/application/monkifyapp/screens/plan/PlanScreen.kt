package com.application.monkify.screens.plan

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.application.monkifyapp.common.MainScaffold
import com.application.monkifyapp.screens.home.components.Calendar
import com.application.monkifyapp.screens.home.components.GlassmorpismCard
import com.application.monkifyapp.screens.home.components.HomeCardTitle
import com.application.monkifyapp.screens.home.components.HomeTitle

@Composable
fun PlanScreen(navController:NavController,selectedTab:Int,onTabSelected: (Int) -> Unit) {
    MainScaffold(navController = navController,selectedTab,onTabSelected =onTabSelected) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(24.dp)
        ) {
            HomeTitle()
            GlassmorpismCard() {
                Column {
                    HomeCardTitle()
                }
            }
        }
    }
}