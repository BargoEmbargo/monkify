package com.application.monkify.screens.home

import android.annotation.SuppressLint
import android.util.Range
import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.application.monkifyapp.R
import com.application.monkifyapp.common.BackgroundImage
import com.application.monkifyapp.common.TopAppBarMonkify
import com.application.monkifyapp.ui.theme.robotoFontFamily
import com.maxkeppeker.sheets.core.models.base.Header
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarView
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import java.time.LocalDate


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val selectedDateRange = remember {
        val value = Range(LocalDate.now().minusDays(2), LocalDate.now())
        mutableStateOf(value)
    }
    var sheetState = rememberSheetState()
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { TopAppBarMonkify()}
        ) {
            BackgroundImage(image = painterResource(id = R.drawable.home_background))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .padding(24.dp)// This ensures the Column takes up the full size of its parent (Scaffold)
            ) {
                Text(
                    modifier = Modifier.padding(top = 24.dp, bottom = 10.dp),
                    text = "Welcome Back!",
                    color = Color.White,
                    fontSize = 32.sp,
                    fontFamily= robotoFontFamily,
                    fontWeight = FontWeight.Bold
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(height = 520.dp, width = 10.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .border(
                            2.dp, brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.White.copy(alpha = 0.3f),
                                    Color.White.copy(alpha = 0.75f)
                                ),
                                startY = 0.0f,
                                endY = 400.0f
                            ), RoundedCornerShape(20.dp)
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
                    Column() {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Column() {
                                Text(
                                    text = "4",
                                    modifier = Modifier.padding(start = 24.dp, top = 24.dp),
                                    color=Color.White,
                                    fontSize=34.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Streak Days",
                                    modifier = Modifier.padding(start = 24.dp),
                                    color=Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = "icon",
                                modifier = Modifier.padding(end = 24.dp, top = 24.dp).size(50.dp)
                            )
                        }
                        Text(
                            text = "Good job!\nThis is your longest streak so far \uD83D\uDE0D",
                            modifier = Modifier.padding(start = 24.dp, top = 10.dp),
                            color=Color.White,

                        )

                        CalendarView(
                            header= Header.Custom{
                            },
                            sheetState = sheetState,
                            config= CalendarConfig(
                                style = CalendarStyle.MONTH,
                                monthSelection = true,
                                yearSelection = true
                            ),
                            selection = CalendarSelection.Period(
                                selectedRange = selectedDateRange.value
                            ) { startDate, endDate ->
                                selectedDateRange.value = Range(startDate, endDate)
                            }
                        )
                    }
                }
            }
        }
}
@Preview
@Composable
fun Proba() {

}

