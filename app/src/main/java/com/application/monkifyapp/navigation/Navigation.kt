package com.application.monkifyapp.navigation

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.application.monkify.screens.home.HomeScreen
import com.application.monkify.screens.plan.PlanScreen
import com.application.monkifyapp.screens.onBoarding.BoardingScreen
import com.application.monkifyapp.screens.onBoarding.viewModel.OnBoardingViewModel
import com.application.monkifyapp.screens.plan.viewmodel.PlanViewModel
import com.application.monkifyapp.screens.task.TaskScreen
import com.application.monkifyapp.screens.task.TaskViewModel

@Composable
fun Navigation(startDestination:String) {
    val navController= rememberNavController()
    val viewModel: OnBoardingViewModel = hiltViewModel()
    var selectedTab by rememberSaveable { mutableStateOf(0) }
    val taskViewModel= androidx.lifecycle.viewmodel.compose.viewModel<TaskViewModel>()
    val planViewModel =androidx.lifecycle.viewmodel.compose.viewModel<PlanViewModel>()
    val route = NavigationGraph.TaskScreen.name

    NavHost(navController = navController, startDestination = startDestination ){
        composable(NavigationGraph.HomeScreen.name){
            HomeScreen(navController = navController,selectedTab){newTab->
                selectedTab=newTab
            }
        }
        composable(NavigationGraph.PlanScreen.name){
            PlanScreen(navController = navController,
                selectedTab=selectedTab,
                planViewModel = planViewModel,
                event = {
                    planViewModel.onEvent(it)
                    println(planViewModel.daysCompleted.value)
                }
            ){newTab->
                selectedTab=newTab
            }
        }
        composable(NavigationGraph.BoardingScreen.name){
            BoardingScreen(navController = navController, event = {
                viewModel.onEvent(it)
            })
        }
        composable("$route/{id}",
            arguments = listOf(navArgument(name = "id"){
                type= NavType.IntType
                defaultValue= 0
            })
        ){backStackEntry ->
            // Your composable content
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            TaskScreen(navController=navController,taskViewModel=taskViewModel,id=id)
        }
    }
}