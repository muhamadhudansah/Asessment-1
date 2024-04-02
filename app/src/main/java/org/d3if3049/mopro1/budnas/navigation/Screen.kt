package org.d3if3049.mopro1.budnas.navigation

sealed class Screen (val route:String) {
    data object Home: Screen("mainScreen")
    data object About: Screen("aboutScreen")
}