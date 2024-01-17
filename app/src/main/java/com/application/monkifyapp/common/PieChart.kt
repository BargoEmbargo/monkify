package com.application.monkifyapp.common

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.application.monkifyapp.ui.theme.Cyan
import java.lang.Math.cos
import java.lang.Math.sin

@Composable
fun PieChart(
    data: Map<String, Int>,
    radiusOuter: Dp = 40.dp,
    chartBarWidth: Dp = 12.dp,
    animDuration: Int = 1000,
) {
    val totalSum = data.values.sum()
    val floatValue = mutableListOf<Float>()

    // To set the value of each Arc according to
    // the value given in the data, we have used a simple formula.
    // For a detailed explanation check out the Medium Article.
    // The link is in the about section and readme file of this GitHub Repository
    data.values.forEachIndexed { index, values ->
        floatValue.add(index, 360 * values.toFloat() / totalSum.toFloat())
    }

    // add the colors as per the number of data (no. of pie chart entries)
    // so that each data will get a color
    val colors = listOf(
        Color.White.copy(alpha = 0.7f),
        Cyan
    )

    var animationPlayed by remember { mutableStateOf(false) }

    // it is the diameter value of the Pie
    val animateSize by animateFloatAsState(
        targetValue = if (animationPlayed) radiusOuter.value * 2f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        )
    )

    // if you want to stabilize the Pie Chart you can use value -90f
    // 90f is used to complete 1/4 of the rotation
    val animateRotation by animateFloatAsState(
        targetValue = if (animationPlayed) 360f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        )
    )

    // to play the animation only once when the function is Created or Recomposed
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Details Pie Chart
        DetailsPieChart(
            data = data,
            colors = colors,
            modifier = Modifier.weight(1f) // This makes the DetailsPieChart take the remaining width
        )

        // Pie Chart using Canvas Arc
        Box(
            modifier = Modifier.size(animateSize.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier
                    .size(radiusOuter * 2f)
                    .rotate(animateRotation)
            ) {
                var lastValue = 0f
                // draw each Arc for each data entry in Pie Chart
                floatValue.forEachIndexed { index, value ->
                    drawArc(
                        color = colors[index],
                        lastValue,
                        value,
                        useCenter = false,
                        style = Stroke(chartBarWidth.toPx(), cap = StrokeCap.Butt)
                    )
                    lastValue += value
                }

                // Draw percentage text in the center
                val centerText = "${(data.values.toList()[0] * 100 / totalSum)}%"
                drawIntoCanvas { canvas ->
                    val textPaint = androidx.compose.ui.graphics.Paint().asFrameworkPaint().apply {
                        color = Color.White.toArgb()
                        textSize = 20.dp.toPx()
                        textAlign = android.graphics.Paint.Align.CENTER
                    }
                    val textBaselineOffset =
                        (size.height / 2) - (textPaint.fontMetrics.ascent + textPaint.fontMetrics.descent) / 2
                    canvas.nativeCanvas.drawText(
                        centerText,
                        size.width / 2,
                        textBaselineOffset,
                        textPaint
                    )
                }
            }
        }
    }
}



@Composable
fun DetailsPieChart(
    data: Map<String, Int>,
    colors: List<Color>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        // create the data items
        data.values.forEachIndexed { index, value ->
            DetailsPieChartItem(
                data = Pair(data.keys.elementAt(index), value),
                color = colors[index]
            )
        }
    }
}

@Composable
fun DetailsPieChartItem(
    data: Pair<String, Int>,
    height: Dp = 14.dp,
    color: Color
) {

    Surface(
        modifier = Modifier
            .padding(vertical = 1.dp, horizontal = 4.dp),
        color = Color.Transparent
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .background(
                        color = color,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .size(height)
            )

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.padding(start = 15.dp),
                    text = data.first,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Text(
                    modifier = Modifier.padding(start = 15.dp),
                    text = data.second.toString(),
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

        }

    }
}

@Preview
@Composable
fun PieChartPreview() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        PieChart(
            data = mapOf(
                Pair("Sample-1", 100),
                Pair("Sample-2", 30),
            )
        )

    }
}