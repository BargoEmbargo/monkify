package com.application.monkifyapp.screens.task


import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.application.monkifyapp.R

@Composable
fun TaskScreen(navController: NavController) {
    Text(text = "mad")
    // Replace "fire.json" with the actual name of your Lottie animation file
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.fire))
    var isPlaying by remember{
        mutableStateOf(true)
    }

    LottieAnimation(composition = composition)



}


@Composable
@Preview
fun TaskScreenPreview() {
    TaskScreen(navController = rememberNavController())
}