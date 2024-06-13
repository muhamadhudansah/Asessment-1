package org.d3if3049.asessment.myapplication.navigation

import org.d3if3049.asessment.myapplication.screenBudnas.KEY_JUDUL

sealed class Screen (val route:String) {
    data object Awal: Screen("awalScreen")
    data object Home2: Screen("mainScreen")
    data object Home: Screen("mainScreen2")
    data object About: Screen("aboutScreen")
    data object FormBaru: Screen("detailScreen")
    data object FormUbah: Screen("detailScreen/{$KEY_JUDUL}"){
        fun withJudul(judul: String) = "detailScreen/$judul"
    }
}