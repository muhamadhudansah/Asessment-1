package org.d3if3049.mopro1.budnas.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.d3if3049.mopro1.budnas.ui.screen.AboutScreen
import org.d3if3049.mopro1.budnas.ui.screen.AwalScreen
import org.d3if3049.mopro1.budnas.ui.screen.DetailScreen
import org.d3if3049.mopro1.budnas.ui.screen.KEY_JUDUL
import org.d3if3049.mopro1.budnas.ui.screen.MainScreen

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