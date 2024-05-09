package org.d3if3049.mopro1.budnas.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if3049.mopro1.budnas.database.BudnasDao
import org.d3if3049.mopro1.budnas.ui.screen.DetailViewModel
import org.d3if3049.mopro1.budnas.ui.screen.MainViewModel

class ViewModelFactory(
    private val dao: BudnasDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(dao) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return  DetailViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}