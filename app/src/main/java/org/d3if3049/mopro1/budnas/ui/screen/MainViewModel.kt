package org.d3if3049.mopro1.budnas.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import org.d3if3049.mopro1.budnas.database.BudnasDao
import org.d3if3049.mopro1.budnas.model.Budnas

class MainViewModel(dao: BudnasDao) : ViewModel() {

    val data: StateFlow<List<Budnas>> = dao.getBudnas().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
}