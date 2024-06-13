package org.d3if3049.asessment.myapplication.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if3049.asessment.myapplication.database.BudnasDao
import org.d3if3049.asessment.myapplication.screenBudnas.DetailViewModel
import org.d3if3049.asessment.myapplication.screenBudnas.MainViewModel2

class ViewModelFactory(
    private val dao: BudnasDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel2::class.java)) {
            return MainViewModel2(dao) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return  DetailViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}