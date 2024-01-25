package com.application.monkify.screens.home

import android.annotation.SuppressLint
import android.util.Range
import androidx.compose.foundation.layout.*
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDrawerState
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


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
@Composable
fun HomeScreen(
    navController: NavController,
    daysCompleted: Int,
    drawerState: DrawerState,
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
) {
    val selectedDateRange = remember {
        val value = if(daysCompleted>0){Range(LocalDate.now().minusDays(daysCompleted.toLong() -1), LocalDate.now())}
        else{Range(LocalDate.now(), LocalDate.now())}
        mutableStateOf(value)
    }
    var sheetState = rememberSheetState()
       MainScaffold(
           navController = navController,
           selectedTab=selectedTab,
           onTabSelected = onTabSelected,
           drawerState=drawerState
       ) {
           Column(
               modifier = Modifier
                   .fillMaxSize()
                   .statusBarsPadding()
                   .padding(24.dp)
           ) {
               Title("Welcome Back!")
               GlassmorpismCard(size=520.dp) {
                   Column {
                       HomeCardTitle(daysCompleted = daysCompleted)
                       Calendar(sheetState = sheetState,selectedDateRange=selectedDateRange)
                   }
               }
           }
       }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun Proba() {
    HomeScreen(navController = rememberNavController(), selectedTab = 0, onTabSelected ={} , daysCompleted = 0, drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed,
    ))
}

