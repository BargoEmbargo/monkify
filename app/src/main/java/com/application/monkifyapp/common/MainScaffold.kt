package com.application.monkifyapp.common

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.application.monkifyapp.R
import com.application.monkifyapp.ui.theme.Cyan

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(
    navController: NavController,
    selectedTab:Int,
    onTabSelected: (Int) -> Unit,
    drawerState: DrawerState ,
    settingsButtonClicked: () -> Unit = {},
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        drawerState=drawerState,
        scrimColor = Color.Black.copy(alpha = 0.6f),
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor= Cyan
            ) {
            Text(text = "Working...")
            }
        },

    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { TopAppBarMonkify(settingsButtonClicked, drawerState = drawerState) },
            bottomBar = {
                CustomBottomBar(navController = navController, selectedTab = selectedTab)
                { newTab -> onTabSelected(newTab) }
            }
        ) {
            BackgroundImage(image = painterResource(id = R.drawable.home_background))
            content()
        }
    }
}
