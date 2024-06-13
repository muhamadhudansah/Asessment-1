package org.d3if3049.asessment.myapplication.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.d3if3049.asessment.myapplication.screenBudnas.AboutScreen
import org.d3if3049.asessment.myapplication.screenBudnas.AwalScreen
import org.d3if3049.asessment.myapplication.screenBudnas.DetailScreen
import org.d3if3049.asessment.myapplication.screenBudnas.KEY_JUDUL
import org.d3if3049.asessment.myapplication.screenBudnas.MainScreen2
import org.d3if3049.asessment.myapplication.ui.screen.MainScreen

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.Awal.route
    ) {
        composable(route = Screen.Awal.route){
            AwalScreen(navController)
        }
        composable(route = Screen.About.route) {
            AboutScreen(navController)
        }
        composable(route = Screen.Home.route){
            MainScreen2(navController)
        }
        composable(route = Screen.Home2.route){
            MainScreen(navController)
        }
        composable(route = Screen.FormBaru.route){
            DetailScreen(navController)
        }
        composable(
            route = Screen.FormUbah.route,
            arguments = listOf(
                navArgument(KEY_JUDUL) { type = NavType.StringType }
            )
        ) {navBackStackEntry ->
            val judul = navBackStackEntry.arguments?.getString(KEY_JUDUL)
            DetailScreen(navController, judul)
        }
    }
}