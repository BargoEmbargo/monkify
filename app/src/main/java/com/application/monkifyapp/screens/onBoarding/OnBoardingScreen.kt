package com.application.monkifyapp.screens.onBoarding

import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.application.monkifyapp.R
import com.application.monkifyapp.navigation.NavigationGraph
import com.application.monkifyapp.common.BackgroundImage
import com.application.monkifyapp.presentation.common.OnBoardingButton
import com.application.monkifyapp.screens.onBoarding.components.OnBoardingPage
import com.application.monkifyapp.screens.onBoarding.components.PagerIndicator
import com.application.monkifyapp.screens.onBoarding.components.onBoardingPages
import com.application.monkifyapp.screens.onBoarding.viewModel.OnBoardingEvent
import kotlinx.coroutines.launch

@OptIn( ExperimentalFoundationApi::class)
@Composable
fun BoardingScreen(
    navController: NavHostController,
    event:(OnBoardingEvent) -> Unit
) {
    val pagerState = rememberPagerState(initialPage = 0) {
        onBoardingPages.size
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        BackgroundImage(image = painterResource(id = R.drawable.background))
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {

            val buttonState = remember {
                derivedStateOf {
                    when (pagerState.currentPage) {
                        0 -> listOf("    ", "Next")
                        1 -> listOf("Back", "Next")
                        2 -> listOf("Back", "Finish")
                        else -> listOf("", "")
                    }
                }
            }
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f) // Take up all available vertical space
            ) { index ->
                OnBoardingPage(page = onBoardingPages[index])
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp)
                    .padding(bottom = 8.dp)
                    .navigationBarsPadding(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val scope = rememberCoroutineScope()
                if(buttonState.value[0].isNotEmpty()) {
                    OnBoardingButton(
                        text = buttonState.value[0],
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(page = pagerState.currentPage - 1)
                            }
                        }
                    )
                }
                PagerIndicator(
                    modifier = Modifier.width(52.dp),
                    pagesSize = onBoardingPages.size,
                    selectedPage = pagerState.currentPage
                )
                OnBoardingButton(
                    text = buttonState.value[1],
                    onClick = {
                        scope.launch {
                            if(pagerState.currentPage==2){
                                navController.navigate(NavigationGraph.HomeScreen.name)
                                event(OnBoardingEvent.SaveAppEntry)
                            }
                            else{
                                pagerState.animateScrollToPage(page = pagerState.currentPage +1)
                            }
                        }
                    }
                )
            }
        }
    }
}





//@Preview
//@Composable
//fun OnBoardingScreenPreview() {
//    BoardingScreen(navController = rememberNavController())
//}

