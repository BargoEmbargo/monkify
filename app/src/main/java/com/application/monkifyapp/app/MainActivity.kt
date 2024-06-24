package com.application.monkifyapp.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.application.monkifyapp.data.local.ToggleableInfoDao
import com.application.monkifyapp.domain.model.ToggleableInfo
import com.application.monkifyapp.navigation.Navigation
import com.application.monkifyapp.ui.theme.MonkifyAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().apply {
            setKeepOnScreenCondition{
                viewModel.splashCondition
            }
        }
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        setContent {
            MonkifyAppTheme {
                val startDestination=viewModel.startDestination
                val daysCompleted=viewModel.daysCompleted
                viewModel.showSimpleNotification()
                Navigation(startDestination = startDestination,daysCompleted=daysCompleted)
            }
        }
    }
}
