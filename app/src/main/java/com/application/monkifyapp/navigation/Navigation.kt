package com.application.monkifyapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.application.monkify.screens.home.HomeScreen
import com.application.monkify.screens.plan.PlanScreen
import com.application.monkifyapp.screens.onBoarding.BoardingScreen
import com.application.monkifyapp.screens.onBoarding.viewModel.OnBoardingViewModel

@Composable
fun Navigation(startDestination:String) {
    val navController= rememberNavController()
    val viewModel: OnBoardingViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = startDestination ){
        composable(NavigationGraph.HomeScreen.name){
            HomeScreen()
        }
        composable(NavigationGraph.PlanScreen.name){
            PlanScreen()
        }
        composable(NavigationGraph.BoardingScreen.name){
            BoardingScreen(navController = navController, event = {
                viewModel.onEvent(it)
            })
        }
    }
}