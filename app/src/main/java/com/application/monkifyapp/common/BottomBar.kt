package com.application.monkifyapp.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.application.monkifyapp.navigation.NavigationGraph
import com.application.monkifyapp.ui.theme.Cyan

@Composable
fun CustomBottomBar(
    navController: NavController,
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    val tabs = listOf(
        Icons.Default.Home,
        Icons.Default.Menu,
    )

    BottomAppBar(
        modifier = Modifier
            .padding(16.dp)
            .navigationBarsPadding()
            .background(
                color = Color.Transparent
            )
            .height(72.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(50)),
        containerColor = Cyan
    ) {
        tabs.forEachIndexed { index, icon ->
            NavigationBarItem(
                icon = {
                    if(selectedTab == index)Icon(icon, contentDescription = null, tint = Cyan)
                    else{Icon(icon, contentDescription = null, tint = Color.White)}
                       },
                colors=NavigationBarItemDefaults.colors(indicatorColor = Color.White),
                selected = selectedTab == index,
                onClick = {
                    if (selectedTab == index) {
                        // Already on the selected tab, no need to recompose or navigate
                        return@NavigationBarItem
                    }
                    onTabSelected(index)
                    when (index) {
                        0 -> navController.navigate(NavigationGraph.HomeScreen.name)
                        1 -> navController.navigate(NavigationGraph.PlanScreen.name)
                        // Add more cases for other tabs as needed
                        else -> {}
                    }
                },
                alwaysShowLabel = false
            )
        }
    }
}