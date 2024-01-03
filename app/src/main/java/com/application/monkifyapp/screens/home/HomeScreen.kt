package com.application.monkify.screens.home

import android.annotation.SuppressLint
import android.util.Range
import android.widget.Space
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.application.monkifyapp.R
import com.application.monkifyapp.common.BackgroundImage
import com.application.monkifyapp.common.CustomBottomBar
import com.application.monkifyapp.common.MainScaffold
import com.application.monkifyapp.common.TopAppBarMonkify
import com.application.monkifyapp.navigation.NavigationGraph
import com.application.monkifyapp.screens.home.components.Calendar
import com.application.monkifyapp.screens.home.components.GlassmorpismCard
import com.application.monkifyapp.screens.home.components.HomeCardTitle
import com.application.monkifyapp.screens.home.components.HomeTitle
import com.application.monkifyapp.ui.theme.Cyan
import com.application.monkifyapp.ui.theme.robotoFontFamily
import com.maxkeppeker.sheets.core.models.base.Header
import com.maxkeppeker.sheets.core.models.base.SheetState
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarView
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import java.time.LocalDate


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
@Composable
fun HomeScreen(navController: NavController,selectedTab:Int,onTabSelected: (Int) -> Unit,) {
    val selectedDateRange = remember {
        val value = Range(LocalDate.now().minusDays(2), LocalDate.now())
        mutableStateOf(value)
    }
    var sheetState = rememberSheetState()
       MainScaffold(navController = navController,selectedTab, onTabSelected = onTabSelected) {
           Column(
               modifier = Modifier
                   .fillMaxSize()
                   .statusBarsPadding()
                   .padding(24.dp)
           ) {
               HomeTitle()
               GlassmorpismCard() {
                   Column {
                       HomeCardTitle()
                       Calendar(sheetState = sheetState,selectedDateRange=selectedDateRange)
                   }
               }
           }
       }
}


@Preview
@Composable
fun Proba() {

}

