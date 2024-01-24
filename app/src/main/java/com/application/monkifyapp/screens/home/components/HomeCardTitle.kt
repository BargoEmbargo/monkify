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
import com.application.monkifyapp.domain.model.AchievementEmojis

@Composable
fun HomeCardTitle(daysCompleted:Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column() {
            Text(
                text = daysCompleted.toString(),
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
        LoopingLottieAnimation(animationResId = chooseAchievementEmoji(daysCompleted))
    }
    Text(
        text = chooseAchievementText(daysCompleted),
        modifier = Modifier.padding(start = 24.dp, top = 10.dp),
        color= Color.White,
        )
}

fun chooseAchievementEmoji(value :Int) : Int {
    return when(value){
         0 ->AchievementEmojis.Sad.res
        in 1..7->AchievementEmojis.Cool.res
        in 8..15->AchievementEmojis.Fire.res
        in 15..10000-> AchievementEmojis.Wow.res
        else -> AchievementEmojis.Sad.res
    }
}
fun chooseAchievementText(value :Int) : String {
    return when(value){
         0 ->"Don't worry!\nToday is the perfect day to start your streak \uD83D\uDCAA!"
        in 1..7->"Keep it up!\nYou're on a roll \uD83D\uDE0E!"
        in 8..15->"Impressive!\nYou're building a habit \uD83D\uDC4D."
        in 15..10000-> "Good job!\nThis is your longest streak so far \uD83D\uDE0D."
        else ->"Don't worry!\nToday is the perfect day to start your streak \uD83D\uDCAA!"
    }
}