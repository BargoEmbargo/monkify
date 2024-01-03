package com.application.monkifyapp.screens.onBoarding.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.application.monkifyapp.ui.theme.robotoFontFamily


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingPage(
    modifier: Modifier = Modifier,
    page: Page,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = page.title,
                fontSize = 30.sp,
                fontWeight= FontWeight.SemiBold,
                fontFamily= robotoFontFamily,
                color = Color.White
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = page.pageImage),
                contentDescription = "image",
                modifier = Modifier
                    .size(210.dp)
                    .clip(shape = CircleShape)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = page.description,
                modifier = Modifier.padding(end = 60.dp),
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.weight(0.3f))
        }
    }
}