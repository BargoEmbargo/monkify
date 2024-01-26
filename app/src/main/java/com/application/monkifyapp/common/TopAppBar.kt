package com.application.monkifyapp.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarMonkify(drawerState: DrawerState) {
    val scope = rememberCoroutineScope()
    SmallTopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(Color.Transparent),
        title={},
        modifier = Modifier.statusBarsPadding(),
        actions = {
            IconButton(
                onClick = {
                    scope.launch {
                        drawerState.open()
                    }
                          },
                modifier = Modifier.padding(end = 6.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "TopBar icon",
                    tint = Color.White
                )
            }

        }
    )
}
