package com.application.monkifyapp.screens.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.application.monkify.screens.plan.LoopingLottieAnimation
import com.application.monkifyapp.R

@Composable
fun HomeCardTitle() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column() {
            Text(
                text = "4",
                modifier = Modifier.padding(start = 24.dp, top = 24.dp),
                color= Color.White,
                fontSize=34.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Streak Days",
                modifier = Modifier.padding(start = 24.dp),
                color= Color.White,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        LoopingLottieAnimation(animationResId = R.raw.fire)
    }
    Text(
        text = "Good job!\nThis is your longest streak so far \uD83D\uDE0D.",
        modifier = Modifier.padding(start = 24.dp, top = 10.dp),
        color= Color.White,
        )
}