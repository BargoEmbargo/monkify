package com.application.monkifyapp.screens.home.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.application.monkifyapp.ui.theme.robotoFontFamily

@Composable
fun HomeTitle() {
    Text(
        modifier = Modifier.padding(top = 24.dp, bottom = 10.dp),
        text = "Welcome Back!",
        color = Color.White,
        fontSize = 32.sp,
        fontFamily= robotoFontFamily,
        fontWeight = FontWeight.Bold
    )
}