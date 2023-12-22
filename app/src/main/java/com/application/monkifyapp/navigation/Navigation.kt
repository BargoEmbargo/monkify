package com.application.monkifyapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.application.monkify.screens.home.HomeScreen
import com.application.monkify.screens.plan.PlanScreen
import com.application.monkifyapp.screens.onBoarding.BoardingScreen

@Composable
fun Navigation() {
    val navController= rememberNavController()

    NavHost(navController = navController, startDestination = NavigationGraph.BoardingScreen.name ){
        composable(NavigationGraph.HomeScreen.name){
            HomeScreen()
        }
        composable(NavigationGraph.PlanScreen.name){
            PlanScreen()
        }
        composable(NavigationGraph.BoardingScreen.name){
            BoardingScreen(navController = navController)
        }
    }
}