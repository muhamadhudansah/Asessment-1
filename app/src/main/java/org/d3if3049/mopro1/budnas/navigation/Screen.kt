package org.d3if3049.mopro1.budnas.navigation

import org.d3if3049.mopro1.budnas.ui.screen.KEY_JUDUL

sealed class Screen (val route:String) {
    data object Awal: Screen("awalScreen")
    data object Home: Screen("mainScreen")
    data object About: Screen("aboutScreen")
    data object FormBaru: Screen("detailScreen")
    data object FormUbah: Screen("detailScreen/{$KEY_JUDUL}"){
        fun withJudul(judul: String) = "detailScreen/$judul"
    }
}