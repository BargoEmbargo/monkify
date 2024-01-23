package com.application.monkify.screens.home

import android.annotation.SuppressLint
import android.util.Range
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.application.monkifyapp.common.MainScaffold
import com.application.monkifyapp.screens.home.components.*
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import java.time.LocalDate


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
@Composable
fun HomeScreen(navController: NavController,daysCompleted:Int,selectedTab:Int,onTabSelected: (Int) -> Unit,) {
    val selectedDateRange = remember {
        val value = Range(LocalDate.now().minusDays(daysCompleted.toLong() -1), LocalDate.now())
        mutableStateOf(value)
    }
    var sheetState = rememberSheetState()
       MainScaffold(navController = navController,selectedTab, onTabSelected = onTabSelected) {
           Column(
               modifier = Modifier
                   .fillMaxSize()
                   .statusBarsPadding()
                   .padding(24.dp)
           ) {
               Title("Welcome Back!")
               GlassmorpismCard(size=520.dp) {
                   Column {
                       HomeCardTitle(daysCompleted = daysCompleted.toString())
                       Calendar(sheetState = sheetState,selectedDateRange=selectedDateRange)
                   }
               }
           }
       }
}


@Preview
@Composable
fun Proba() {
    HomeScreen(navController = rememberNavController(), selectedTab = 0, onTabSelected ={} , daysCompleted = 0)
}

