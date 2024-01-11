package com.application.monkify.screens.plan

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.application.monkifyapp.common.MainScaffold
import com.application.monkifyapp.domain.model.AchievementEmojis
import com.application.monkifyapp.domain.model.ToggleableInfo
import com.application.monkifyapp.navigation.NavigationGraph
import com.application.monkifyapp.screens.home.components.GlassmorpismCard
import com.application.monkifyapp.screens.home.components.Title
import com.application.monkifyapp.screens.task.TaskViewModel
import com.application.monkifyapp.ui.theme.Cyan
import kotlinx.coroutines.launch
@Composable
fun PlanScreen(
    navController:NavController,
    taskViewModel:TaskViewModel = hiltViewModel(),
    selectedTab:Int,
    onTabSelected: (Int) -> Unit
) {
    val list = taskViewModel.infoList.collectAsState().value
    val scope = rememberCoroutineScope()

    scope.launch {
        taskViewModel.upsertInfo(
            ToggleableInfo(1,true,"PLS WORK")
        )
        taskViewModel.upsertInfo(
            ToggleableInfo(2,false,"PLS WORK1")
        )
    }

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
                           PlanTitle("Achievement")
                           Column() {
                               AchievementText(text = "Mad", color = Cyan)
                               Spacer(modifier = Modifier.height(10.dp))
                               AchievementText(text = list.size.toString(), color = Color.White.copy(alpha = 0.6f))
                           }
                       }
                       Spacer(modifier = Modifier.weight(1f))
                       LoopingLottieAnimation(animationResId = chooseAchievementEmoji(2))
                   }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(verticalAlignment = Alignment.Bottom) {
                PlanTitle("Daily goals")
                Spacer(modifier = Modifier.weight(1f))
                SetupText {
                   navController.navigate(NavigationGraph.TaskScreen.name)
                }
            }
            GlassmorpismCard(size = 170.dp) {
                CheckBoxGoals(checkList = list)
            }
        }
    }
}

@Composable
fun CheckBoxGoals(checkList:List<ToggleableInfo>) {
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp, vertical = 9.dp)) {
        checkList.forEachIndexed { index, toggleableInfo ->
            Row(modifier=Modifier.padding(top = 5.dp), verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = toggleableInfo.isChecked, onCheckedChange = {
                    scope.launch {
//                        checkList[index]=toggleableInfo.copy(isChecked =it)
                        println(toggleableInfo.isChecked)
                    }
                })
                Text(text = toggleableInfo.text)
            }
        }
    }
}

@Composable
fun SetupText(setUpFunction:()->Unit) {
    Text(
        modifier= Modifier
            .padding(end = 10.dp, bottom = 10.dp)
            .clickable {
                setUpFunction()
            },
        text = "Set up",
        fontSize=18.sp,
        fontWeight=FontWeight.SemiBold,
        color = Cyan,
        textDecoration = TextDecoration.Underline
    )
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
fun LoopingLottieAnimation(animationResId: Int) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(animationResId))
    composition?.let { composition ->
        LottieAnimation(
            modifier= Modifier
                .padding(top = 24.dp, end = 10.dp)
                .size(70.dp),
            composition = composition,
            iterations = LottieConstants.IterateForever // This will make the animation loop
        )
    }
}

@Composable
fun PlanTitle(text:String) {
    Text(
        modifier = Modifier.padding(bottom = 10.dp),
        text = text,
        color = Color.White,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )
}
fun chooseAchievementEmoji(value :Int) : Int {
    return when(value){
        1-> AchievementEmojis.Fire.res
        2-> AchievementEmojis.Sad.res
        else -> AchievementEmojis.Fire.res
    }
}

@Preview
@Composable
fun PlanScreenPreview() {
    PlanScreen(navController = rememberNavController(), selectedTab = 1, onTabSelected = {})
}