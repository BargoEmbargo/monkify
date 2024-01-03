package com.application.monkifyapp.presentation.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OnBoardingButton(
    text: String,
    onClick: () -> Unit ={}
) {
        TextButton(
            onClick = onClick,
            contentPadding = PaddingValues(0.dp),
        ) {
            Text(
                text = text,
                color = Color.DarkGray,
                fontSize = 16.sp,
            )
        }
}