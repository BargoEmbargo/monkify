package com.application.monkifyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.application.monkifyapp.navigation.Navigation
import com.application.monkifyapp.ui.theme.MonkifyAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        setContent {
            MonkifyAppTheme {

              Navigation()
            }
        }
    }
}
