package com.application.monkifyapp.screens.task


import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.room.util.appendPlaceholders
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.application.monkifyapp.R
import com.application.monkifyapp.common.BackgroundImage
import com.application.monkifyapp.common.CustomBottomBar
import com.application.monkifyapp.common.MainScaffold
import com.application.monkifyapp.common.TopAppBarMonkify
import com.application.monkifyapp.screens.home.components.Title
import com.application.monkifyapp.ui.theme.Cyan

@Composable
fun TaskScreen(navController: NavController) {
    var trytext by remember {
        mutableStateOf("")
    }
    TaskScaffold(navController = navController) {
        Column( modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(24.dp))
        {
            Column(
                modifier=Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Title(title ="Add new task")
                TaskTextFieldInput(
                    nameValue = trytext,
                    onNameChange = {trytext=it},
                    textFieldTitle = "Name"
                )
                Spacer(modifier = Modifier.padding(vertical = 10.dp))
                TaskTextFieldInput(
                    nameValue = trytext,
                    onNameChange = {trytext=it},
                    textFieldTitle = "Description"
                )
                Spacer(modifier = Modifier.padding(vertical = 10.dp))
                DropDownTaskMenu()
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
                singleLine = true,
                maxLines = 1
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownTaskMenu() {
    var isExpanded by remember {
        mutableStateOf(false)
    }
    var menuText by remember {
        mutableStateOf("")
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
                trailingIcon = {ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)},
                colors = ExposedDropdownMenuDefaults.textFieldColors(containerColor = Cyan, textColor = Color.White, unfocusedIndicatorColor = Color.White, focusedIndicatorColor = Color.White, unfocusedTrailingIconColor = Color.White, focusedTrailingIconColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )
            ExposedDropdownMenu(
                modifier=Modifier.background(Cyan),
                expanded = isExpanded,
                onDismissRequest = { isExpanded=false}) {
                DropdownMenuItem(
                    colors = MenuDefaults.itemColors(textColor = Color.White),
                    text = {  Text(text = "Exercise") },
                    onClick = {
                        menuText="Exercise"
                        isExpanded=false }
                )
                DropdownMenuItem(
                    colors = MenuDefaults.itemColors(textColor = Color.White),
                    text = { Text(text = "Studying") },
                    onClick = {
                        menuText="Studying"
                        isExpanded=false }
                )
                DropdownMenuItem(
                    colors = MenuDefaults.itemColors(textColor = Color.White),
                    text = {  Text(text = "Other") },
                    onClick = {
                        menuText="Other"
                        isExpanded=false }
                )
            }
        }
    }
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
    TaskScreen(navController = rememberNavController())
}