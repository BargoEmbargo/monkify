package com.application.monkifyapp.screens.home.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset.Companion.Infinite
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun GlassmorpismCard(
    modifier: Modifier = Modifier,
    size: Dp,
    content: @Composable () -> Unit
) {
    val borderColors = remember { listOf(Color.White.copy(alpha = 0.2f), Color.White.copy(alpha = 0.85f)) }
    val animationDuration = 2000
    var currentColorIndex by remember { mutableStateOf(0) }

    val color = animateColorAsState(
        targetValue = borderColors[currentColorIndex % borderColors.size],
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = animationDuration,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    ).value

    LaunchedEffect(currentColorIndex) {
        delay(animationDuration.toLong())
        currentColorIndex = (currentColorIndex + 1) % borderColors.size
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .size(height = size, width = 1.dp)
            .clip(RoundedCornerShape(20.dp))
            .border(
                2.dp, brush = Brush.verticalGradient(
                    colors = listOf(color, color),
                    startY = 0.0f,
                    endY = 400.0f
                ), shape = RoundedCornerShape(20.dp)
            )
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.3f),
                        Color.White.copy(alpha = 0.35f)
                    ),
                    startY = 100.0f,
                    endY = 400.0f
                )
            )
    ) {
        content()
    }
}