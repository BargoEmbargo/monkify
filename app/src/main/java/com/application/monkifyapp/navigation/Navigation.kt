package com.application.monkifyapp.navigation

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.application.monkify.screens.home.HomeScreen
import com.application.monkify.screens.plan.PlanScreen
import com.application.monkifyapp.screens.onBoarding.BoardingScreen
import com.application.monkifyapp.screens.onBoarding.viewModel.OnBoardingViewModel
import com.application.monkifyapp.screens.task.TaskScreen

@Composable
fun Navigation(startDestination:String) {
    val navController= rememberNavController()
    val viewModel: OnBoardingViewModel = hiltViewModel()
    var selectedTab by rememberSaveable { mutableStateOf(0) }

    NavHost(navController = navController, startDestination = startDestination ){
        composable(NavigationGraph.HomeScreen.name){
            HomeScreen(navController = navController,selectedTab){newTab->
                selectedTab=newTab
            }
        }
        composable(NavigationGraph.PlanScreen.name){
            PlanScreen(navController = navController,selectedTab=selectedTab){newTab->
                selectedTab=newTab
            }
        }
        composable(NavigationGraph.BoardingScreen.name){
            BoardingScreen(navController = navController, event = {
                viewModel.onEvent(it)
            })
        }
        composable(NavigationGraph.TaskScreen.name){
            TaskScreen(navController=navController)
        }
    }
}