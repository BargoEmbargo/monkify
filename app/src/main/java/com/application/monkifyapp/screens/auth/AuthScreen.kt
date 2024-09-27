package com.application.monkifyapp.screens.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.application.monkifyapp.R
import com.application.monkifyapp.common.BackgroundImage
import com.application.monkifyapp.ui.theme.Cyan

@Composable
fun AuthScreen(modifier: Modifier = Modifier) {
    Box(modifier = Modifier.fillMaxSize()){
        BackgroundImage(image = painterResource(id = R.drawable.home_background))
        Column(
            modifier=Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(modifier = Modifier.weight(0.5f))
            Image(
                modifier=Modifier.size(100.dp),
                painter = painterResource(id = R.drawable.google),
                contentDescription = "Google Icon"
            )
            AuthScreenText(
                modifier = Modifier.padding(top = 20.dp),
                text = "Welcome!",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            AuthScreenText(
                modifier = Modifier.padding(top=10.dp),
                text = "Please sign in with google",
                color = Color.Gray.copy(alpha = 0.5f),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.weight(0.5f))
            Button(
                modifier= Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp, horizontal = 24.dp),
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(Color.Transparent),
                border = BorderStroke(2.dp, Cyan)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier=Modifier.padding(vertical = 6.dp)
                ) {
                    Image(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = R.drawable.google),
                        contentDescription ="Google Icon"
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 10.dp))
                    Text(text = "Sign in with Google")
                }
            }
        }
    }
}

@Composable
fun AuthScreenText(
    modifier: Modifier = Modifier,
    text:String,
    color:Color,
    fontSize: TextUnit,
    fontWeight:FontWeight) {
    Text(
        modifier=modifier,
        text = text,
        color=color,
        fontSize = fontSize,
        fontWeight = fontWeight
    )
}

@Preview
@Composable
fun AuthScreenPreview(modifier: Modifier = Modifier) {
    AuthScreen()
}
