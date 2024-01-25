package com.application.monkify.screens.plan

import android.os.Looper
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.application.monkifyapp.common.MainScaffold
import com.application.monkifyapp.common.PieChart
import com.application.monkifyapp.domain.model.ToggleableInfo
import com.application.monkifyapp.navigation.NavigationGraph
import com.application.monkifyapp.screens.home.components.GlassmorpismCard
import com.application.monkifyapp.screens.home.components.Title
import com.application.monkifyapp.screens.plan.viewmodel.DaysCompletedEvent
import com.application.monkifyapp.screens.plan.viewmodel.PlanViewModel
import com.application.monkifyapp.domain.model.CategoryTask
import com.application.monkifyapp.ui.theme.Cyan
import kotlinx.coroutines.launch

@Composable
fun PlanScreen(
    navController:NavController,
    planViewModel: PlanViewModel = hiltViewModel(),
    event:(DaysCompletedEvent) -> Unit,
    selectedTab:Int,
    onTabSelected: (Int) -> Unit
) {


    val list = planViewModel.infoList.collectAsState().value

    val completedTasks = list.count { it.isChecked }
    val inCompletedTasks = list.size - completedTasks
    val scope = rememberCoroutineScope()


    var daysCompleted by remember{
        mutableStateOf(planViewModel.daysCompleted.value)
    }

    var isDayCompleted by remember{
        mutableStateOf(false)
    }

    if(list.size==completedTasks && !isDayCompleted && list.isNotEmpty()){
        daysCompleted++
        planViewModel.updateDaysCompleted(daysCompleted)
        isDayCompleted=true
    }
    var showInfoDialog by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

// Schedule a task to reset isDayCompleted every 10 seconds for testing
    DisposableEffect(Unit) {
        val handler = android.os.Handler(Looper.getMainLooper())
        val resetTask = object : Runnable {
            override fun run() {
                // Update isChecked for all items in the list
                list.forEachIndexed { index, toggleableInfo ->
                    val updatedList = list.toMutableList()
                    updatedList[index] = toggleableInfo.copy(isChecked = false)
                    planViewModel.updateInfoList(updatedList)
                    scope.launch {
                        planViewModel.upsertInfo(toggleableInfo.copy(isChecked = false))
                    }
                }
                isDayCompleted = false
                handler.postDelayed(this, 50 * 1000) // Repeat every 10 seconds for testing
                print(isDayCompleted)
            }
        }

        handler.postDelayed(resetTask, 50 * 1000) // Initial delay for the first execution

        onDispose {
            handler.removeCallbacks(resetTask)
        }
    }
    event(DaysCompletedEvent.SaveDaysCompleted)
    if(showInfoDialog){
        TaskCheckingAlertDialog {
            showInfoDialog=false
        }
    }
    MainScaffold(navController = navController,selectedTab,onTabSelected =onTabSelected) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(24.dp)
        ) {
            Title("Here is your plan:")
            
            GlassmorpismCard(size = if(list.isNotEmpty()){135.dp}else{100.dp}) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp, vertical = 14.dp)

                ) {

                    if(list.isNotEmpty()){
                        PieChart(
                            data = mapOf(
                                Pair("$inCompletedTasks tasks remaining!", inCompletedTasks),
                                Pair("$completedTasks tasks completed", completedTasks),
                            )
                        )
                    }
                    else{
                        ifEmptyText(text = "No data...")
                    }

                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(verticalAlignment = Alignment.Bottom) {
                Row(verticalAlignment = Alignment.CenterVertically){
                    PlanTitle("Daily goals")
                    Icon(
                        modifier= Modifier
                            .padding(bottom = 10.dp, start = 10.dp)
                            .clickable { showInfoDialog = true },
                        tint=Color.Gray.copy(alpha = 0.7f),
                        imageVector = Icons.Default.Info, contentDescription ="info_icon" )
                }

                Spacer(modifier = Modifier.weight(1f))
                SetupText {
                    navController.navigate("${NavigationGraph.TaskScreen.name}/-1")
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            GlassmorpismCard(size = calculateCardHeight(itemCount = list.size)) {
                if(list.isNotEmpty()){
                    CheckBoxGoals(checkList = list,scrollState=scrollState, planViewModel = planViewModel){id->
                        navController.navigate("${NavigationGraph.TaskScreen.name}/$id")
                    }
                }
                else{
                    ifEmptyText(text = "Please set up your daily goal")
                }

            }
        }
    }
}

@Composable
fun TaskCheckingAlertDialog(
    onDismiss: () -> Unit
) {
    AlertDialog(
        containerColor = Color.White,
        titleContentColor = Color.Black,
        textContentColor = Color.Black.copy(alpha = 0.8f),
        onDismissRequest = { onDismiss() },
        title = {
            Text("Task checking")
        },
        text = {
            Text(
                "Don't forget to check your tasks daily or your progress won't be recorded, and you will have to start over again!"
            )
        },
        dismissButton = {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = { onDismiss() }
                ) {
                    Text(
                        color=Cyan,
                       text= "I understand")
                }
            }
        },
        confirmButton = {}
    )
}

@Composable
fun ifEmptyText(text:String) {
    Column(modifier=Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = text,
            color = Color.White.copy(alpha = 0.5f),
            fontWeight = FontWeight.Bold
        )
    }
}
@Composable
fun calculateCardHeight(itemCount: Int): Dp {
    return if(itemCount==0){
        100.dp
    }
    else if(itemCount>5){
        315.dp
    }
    else{
        (itemCount * 59).dp
    }
}

@Composable
fun CheckBoxGoals(checkList:List<ToggleableInfo>,scrollState: ScrollState,planViewModel: PlanViewModel,onRowClicked:(Int)->Unit) {
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp, vertical = 9.dp)
            .verticalScroll(state = scrollState)
    ) {
        checkList.forEachIndexed { index, toggleableInfo ->
            Row(
                modifier = Modifier
                    .padding(top = 5.dp, start = 14.dp, end = 14.dp)
                    .clickable { onRowClicked(toggleableInfo.id) },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${index + 1}.",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Checkbox(
                    colors = CheckboxDefaults.colors(Cyan),
                    checked = toggleableInfo.isChecked,
                    onCheckedChange = {
                        scope.launch {
                            val updatedList = checkList.toMutableList()
                            updatedList[index] = toggleableInfo.copy(isChecked = it)
                            // Update the state using the viewModel
                            println(toggleableInfo.id)
                            planViewModel.updateInfoList(updatedList)
                            planViewModel.upsertInfo(toggleableInfo.copy(isChecked = it))
                        }
                    }
                )
                Text(
                    modifier = Modifier
                        .weight(1f) // This will make the text take up the available space
                        .padding(end = 14.dp), // Adjust the padding as needed
                    text = toggleableInfo.descriptionText,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 16.sp,
                    color = Color.White,
                )
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = calculateIcon(toggleableInfo.categoryTask),
                    contentDescription = "Task Icon",
                    tint = Color.White.copy(alpha = 0.7f)
                )
            }

        }
    }
}




fun calculateIcon(categoryName:String):ImageVector{
    return when(categoryName){
        CategoryTask.Exercise.title-> CategoryTask.Exercise.icon
        CategoryTask.Stydying.title-> CategoryTask.Stydying.icon
        CategoryTask.Reading.title-> CategoryTask.Reading.icon
        CategoryTask.PhoneLocked.title-> CategoryTask.PhoneLocked.icon
        CategoryTask.Meditating.title-> CategoryTask.Meditating.icon
        CategoryTask.RidingBike.title-> CategoryTask.RidingBike.icon
        CategoryTask.SkinCare.title-> CategoryTask.SkinCare.icon
        CategoryTask.Running.title-> CategoryTask.Running.icon
        CategoryTask.Other.title-> CategoryTask.Other.icon
        else -> CategoryTask.Exercise.icon
    }
}

@Composable
fun SetupText(setUpFunction:()->Unit) {
    Text(
        modifier= Modifier
            .padding(bottom = 10.dp)
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
fun LoopingLottieAnimation(animationResId: Int) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(animationResId))
    composition?.let { composition ->
        LottieAnimation(
            modifier= Modifier
                .padding(top = 24.dp, end = 24.dp)
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


//@Preview
//@Composable
//fun PlanScreenPreview() {
//    val planViewModel= androidx.lifecycle.viewmodel.compose.viewModel<PlanViewModel>()
//    PlanScreen(navController = rememberNavController(), selectedTab = 1, onTabSelected = {}, planViewModel = planViewModel)
//}