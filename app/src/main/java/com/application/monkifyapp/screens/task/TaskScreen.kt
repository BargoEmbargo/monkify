package com.application.monkifyapp.screens.task


import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.application.monkify.screens.plan.calculateIcon
import com.application.monkifyapp.R
import com.application.monkifyapp.common.*
import com.application.monkifyapp.domain.model.CategoryTask
import com.application.monkifyapp.domain.model.ToggleableInfo
import com.application.monkifyapp.screens.home.components.Title
import com.application.monkifyapp.screens.task.viewmodel.TaskViewModel
import com.application.monkifyapp.ui.theme.Cyan
import kotlinx.coroutines.launch

@Composable
fun TaskScreen(navController: NavController,id:Int,taskViewModel: TaskViewModel = hiltViewModel()) {
    val context = LocalContext.current
    var descriptionText by remember {
        mutableStateOf("")
    }
    var categoryText by remember {
        mutableStateOf("Choose...")
    }
    var isSelectedItem by remember{
        mutableStateOf(false)
    }

    val scope = rememberCoroutineScope()
    if(id>0){
        LaunchedEffect(id) {
            val task= taskViewModel.getInfoById(id)
            isSelectedItem=true
            descriptionText=task.descriptionText
            categoryText=task.categoryTask
        }
    }


    TaskScaffold(navController = navController) {
        Column( modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(24.dp))
        {
            Column(
                modifier=Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Title(title =if(isSelectedItem)"Update Task" else{"Add New Task"})
                Spacer(modifier = Modifier.padding(vertical = 10.dp))
                TaskTextFieldInput(
                    nameValue = descriptionText,
                    onNameChange = {descriptionText=it},
                    textFieldTitle = "Description"
                )
                Spacer(modifier = Modifier.padding(vertical = 10.dp))
                DropDownTaskMenu(categoryText){category->
                    categoryText=category
                }
                Column(
                    modifier=Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    TaskButton(
                        isSelectedItem=isSelectedItem,
                        onAddClick = {
                            if(descriptionText.isNotEmpty() && categoryText!="Choose..."){
                                scope.launch {
                                    if(id>0){
                                        taskViewModel.upsertInfo(ToggleableInfo(id=id,descriptionText=descriptionText, categoryTask = categoryText))
                                        customToastMessage(context = context, message ="Task updated" )
                                    }
                                    else{
                                        taskViewModel.upsertInfo(ToggleableInfo(descriptionText=descriptionText, categoryTask = categoryText))
                                        customToastMessage(context = context, message ="Task added" )
                                    }

                                    descriptionText=""

                                }
                            }else{
                                customToastMessage(context = context, message ="Please add a description and category for the task" )
                            }

                    }, onDeleteClick = {
                        scope.launch {
                            val task=taskViewModel.getInfoById(id = id)
                            if(id>0){
                                taskViewModel.deleteInfo(task)
                                customToastMessage(context = context, message ="Task Deleted" )
                                navController.popBackStack()
                            }else{
                                taskViewModel.deleteAllInfo()
                                taskViewModel.updateInfoList(emptyList())
                                customToastMessage(context = context, message ="All Tasks Deleted" )
                                navController.popBackStack()
                            }
                        }
                    })
                }
            }
        }
    }
}




@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScaffold(
    navController: NavController,
    content: @Composable () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TaskScreenAppBar(navController = navController)}
    ) {
        BackgroundImage(image = painterResource(id = R.drawable.home_background))
        content()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreenAppBar(navController: NavController) {
    TopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(Color.Transparent),
        title= {},
        navigationIcon = { CustomIconButtonForTaskAppBar(
            imageVector = Icons.Default.ArrowBack,
            navController = navController,
            contentDescription = "ArrowBack"
        )}
    )
}

@Composable
fun CustomIconButtonForTaskAppBar(imageVector: ImageVector,navController: NavController,contentDescription:String) {
    IconButton(onClick = { navController.popBackStack() }) {
        Icon(imageVector = imageVector,
            tint = Color.White,
            contentDescription =contentDescription)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskTextFieldInput(nameValue: String = "", onNameChange: (value: String) -> Unit,textFieldTitle:String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        TaskScreenTitle(text = textFieldTitle)
        Box(modifier = Modifier.fillMaxWidth()) {
            BasicTextField(
                value = nameValue,
                onValueChange = { value -> onNameChange(value) },
                textStyle = LocalTextStyle.current.copy(color = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(
                                Color.Transparent,
                                Color.Transparent
                            )
                        )
                    )
                    .drawBehind {
                        drawLine(
                            color = Color.White,
                            start = Offset(0f, size.height),
                            end = Offset(size.width, size.height),
                            strokeWidth = 3f // Adjust the strokeWidth as needed
                        )
                    }
                    .padding(8.dp),
//                singleLine = true,
                maxLines = 2
            )

            if (nameValue.isEmpty()) {
                Text(
                    text = "Enter the name for the task...",
                    color = Color.White.copy(alpha = 0.5f),
                    modifier = Modifier
                        .padding(8.dp)
                        .offset(y = (+3).dp) // Adjust the offset as needed
                )
            }
        }
    }
}


@Composable
fun TaskButton(onAddClick:()->Unit,onDeleteClick:()->Unit,isSelectedItem:Boolean) {
    Row(

    ) {
        Button(
            shape= RoundedCornerShape(4.dp),
            colors= ButtonDefaults.buttonColors(Color(0xFF726FFF)),
            modifier= Modifier
                .weight(1f)
                .height(60.dp),
            onClick = {onAddClick()}
        ) {
            Text(
                text = if(isSelectedItem)"Update" else{"Add"},
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.width(10.dp))
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
        }
        Spacer(modifier = Modifier.width(20.dp))
        Button(
            shape= RoundedCornerShape(4.dp),
            colors= ButtonDefaults.buttonColors(Color(0xFFFF6F6F)),
            modifier= Modifier
                .weight(1f)
                .height(60.dp),
            onClick = {onDeleteClick()}
        ) {
            Text(
                text = if(isSelectedItem)"Delete" else{"Delete All"},
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.width(10.dp))
            Icon(imageVector = Icons.Default.Delete, contentDescription = "Add")
        }
    }
}

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownTaskMenu(categoryText1:String,categoryText:(String)->Unit) {
    var isExpanded by remember {
        mutableStateOf(false)
    }
    var menuText by remember(categoryText1) {
        mutableStateOf(categoryText1)
    }

    LaunchedEffect(categoryText1) {
        menuText = categoryText1
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        TaskScreenTitle(text = "Category")
        Spacer(modifier = Modifier.padding(vertical = 10.dp))
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            modifier = Modifier.fillMaxWidth(),
            onExpandedChange = {isExpanded=it }) {
            TextField(
                value = menuText,
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    Row() {
                        if(menuText!="Choose...")
                        Icon(
                            imageVector = calculateIcon(menuText),
                            contentDescription = "",
                            modifier=Modifier.padding(end = 14.dp)
                        )
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                    }
                               },
                colors = ExposedDropdownMenuDefaults.textFieldColors(containerColor = Cyan, textColor = Color.White, unfocusedIndicatorColor = Color.White, focusedIndicatorColor = Color.White, unfocusedTrailingIconColor = Color.White, focusedTrailingIconColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )
            ExposedDropdownMenu(
                modifier=Modifier.background(Cyan),
                expanded = isExpanded,
                onDismissRequest = { isExpanded=false}) {
                CustomDropDownItem(imageVector = CategoryTask.Exercise.icon, text = CategoryTask.Exercise.title ) {
                    menuText= CategoryTask.Exercise.title
                    categoryText(menuText)
                    isExpanded=false
                }
                CustomDropDownItem(imageVector = CategoryTask.Stydying.icon, text = CategoryTask.Stydying.title  ) {
                    menuText= CategoryTask.Stydying.title
                    categoryText(menuText)
                    isExpanded=false
                }
                CustomDropDownItem(imageVector = CategoryTask.RidingBike.icon, text = CategoryTask.RidingBike.title  ) {
                    menuText= CategoryTask.RidingBike.title
                    categoryText(menuText)
                    isExpanded=false
                }
                CustomDropDownItem(imageVector = CategoryTask.PhoneLocked.icon, text = CategoryTask.PhoneLocked.title  ) {
                    menuText= CategoryTask.PhoneLocked.title
                    categoryText(menuText)
                    isExpanded=false
                }
                CustomDropDownItem(imageVector = CategoryTask.Meditating.icon, text = CategoryTask.Meditating.title  ) {
                    menuText= CategoryTask.Meditating.title
                    categoryText(menuText)
                    isExpanded=false
                }
                CustomDropDownItem(imageVector = CategoryTask.Other.icon, text = CategoryTask.Other.title  ) {
                    menuText= CategoryTask.Other.title
                    categoryText(menuText)
                    isExpanded=false
                }


            }
        }
    }
}

@Composable
fun CustomDropDownItem(imageVector: ImageVector,text:String,onClick:()->Unit) {
    DropdownMenuItem(
        colors = MenuDefaults.itemColors(textColor = Color.White),
        trailingIcon = {
            Icon(
                imageVector = imageVector,
                tint = Color.White,
                contentDescription = "",
                modifier=Modifier.padding(end = 14.dp)
            )},
        text = {  Text(text = text) },
        onClick = {
            onClick()
        }
    )
}

@Composable
fun TaskScreenTitle(text:String) {
    Text(text = text,
        color = Color.White,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    )
}



@Composable
@Preview
fun TaskScreenPreview() {
    TaskScreen(navController = rememberNavController(),id=0)
}