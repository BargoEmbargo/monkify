package com.application.monkify.screens.plan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.application.monkifyapp.common.MainScaffold
import com.application.monkifyapp.screens.home.components.*
import com.application.monkifyapp.ui.theme.Cyan

@Composable
fun PlanScreen(navController:NavController,selectedTab:Int,onTabSelected: (Int) -> Unit) {
    MainScaffold(navController = navController,selectedTab,onTabSelected =onTabSelected) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(24.dp)
        ) {
            Title("Here is your plan:")
            GlassmorpismCard(size = 140.dp) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp, vertical = 14.dp)
                ) {
                   Row(
                       modifier=Modifier.fillMaxWidth(),
                       verticalAlignment = Alignment.CenterVertically,
                   ) {
                       Column {
                           PlanTitle()
                           Column() {
                               AchievementText(text = "Mad", color = Cyan)
                               Spacer(modifier = Modifier.height(10.dp))
                               AchievementText(text = "Nice", color = Color.White.copy(alpha = 0.6f))
                           }
                       }
                       Spacer(modifier = Modifier.weight(1f))
                       Box(modifier = Modifier
                           .size(100.dp)
                           .clip(CircleShape)
                           .background(Cyan)
                          )
                   }
                }
            }
        }
    }
}

@Composable
fun AchievementText(text:String,color:Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(0.5f)
    ) {
        Box(modifier = Modifier
            .size(14.dp)
            .clip(CircleShape)
            .background(color = color)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = text,
            maxLines = 5,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun PlanTitle() {
    Text(
        modifier = Modifier.padding(bottom = 10.dp),
        text = "Achievement",
        color = Color.White,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )
}

@Preview
@Composable
fun PlanScreenPreview() {
    PlanScreen(navController = rememberNavController(), selectedTab = 1, onTabSelected = {})
}