package com.application.monkifyapp.navigation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDrawerState
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
import com.application.monkifyapp.screens.task.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation(startDestination:String,daysCompleted:Int) {

    val navController= rememberNavController()
    val viewModel: OnBoardingViewModel = hiltViewModel()
    var selectedTab by rememberSaveable { mutableStateOf(0) }
    var drawerState= rememberDrawerState(initialValue = DrawerValue.Closed)
    val taskViewModel= androidx.lifecycle.viewmodel.compose.viewModel<TaskViewModel>()
    val planViewModel =androidx.lifecycle.viewmodel.compose.viewModel<PlanViewModel>()
    val route = NavigationGraph.TaskScreen.name

    NavHost(
        navController = navController,
        startDestination = startDestination )
    {
        composable(NavigationGraph.HomeScreen.name){
            HomeScreen(
                navController = navController,
                drawerState=drawerState,
                daysCompleted=daysCompleted,
                selectedTab = selectedTab
            ){ newTab->
                selectedTab=newTab
            }
        }
        composable(NavigationGraph.PlanScreen.name){
            PlanScreen(
                navController = navController,
                selectedTab=selectedTab,
                planViewModel = planViewModel,
                drawerState=drawerState,
                event = {
                    planViewModel.onEvent(it)
                    println(planViewModel.daysCompleted.value)
                }
            ){newTab->
                selectedTab=newTab
            }
        }
        composable(NavigationGraph.BoardingScreen.name){
            BoardingScreen(
                navController = navController,
                event = {
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
            TaskScreen(
                navController=navController,
                taskViewModel=taskViewModel,
                id=id
            )
        }
    }
}