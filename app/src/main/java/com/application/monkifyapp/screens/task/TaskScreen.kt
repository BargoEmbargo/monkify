package com.application.monkifyapp.screens.task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.application.monkifyapp.domain.model.ToggleableInfo

@Composable
fun TaskScreen(navController: NavController) {
    Text(text = "mad")
    val vvzi = remember{
        mutableStateListOf(
            ToggleableInfo(1,true,"1"),
            ToggleableInfo(2,false,"2"),
            ToggleableInfo(3,true,"3")
        )
    }
    Column(modifier = Modifier.padding(top=100.dp)) {
    vvzi.forEachIndexed { index, toggleableInfo ->
            Row(modifier=Modifier.padding(top = 5.dp), verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = toggleableInfo.isChecked, onCheckedChange = {vvzi[index]=toggleableInfo.copy(isChecked =it)})
                Text(text = toggleableInfo.text)
            }
        }
    }

}


@Composable
@Preview
fun TaskScreenPreview() {
    TaskScreen(navController = rememberNavController())
}