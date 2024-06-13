package org.d3if3049.asessment.myapplication.screenBudnas

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import org.d3if3049.asessment.myapplication.R
import org.d3if3049.asessment.myapplication.navigation.Screen
import org.d3if3049.asessment.myapplication.ui.theme.MyApplicationTheme


@Composable
fun AwalScreen(navController: NavController) {
    LaunchedEffect(Unit) {
        delay(2000)
        navController.navigate(Screen.Home.route)
    }
    LogoScreen(navController)
}

@Composable
fun LogoScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.budnas),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(vertical = 10.dp)
        )
    }
}



@Preview(showBackground = true)
@Composable
fun AwalScreenPreview() {
    MyApplicationTheme {
        AwalScreen(rememberNavController())
    }
}