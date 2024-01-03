package com.application.monkifyapp.common

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.application.monkifyapp.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(
    navController: NavController,
    selectedTab:Int,
    onTabSelected: (Int) -> Unit,
    content: @Composable () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopAppBarMonkify() },
        bottomBar = {
            CustomBottomBar(navController = navController,selectedTab=selectedTab)
            { newTab -> onTabSelected(newTab)

            }
        }
    ) {
        BackgroundImage(image = painterResource(id = R.drawable.home_background))
        content()
    }
}
