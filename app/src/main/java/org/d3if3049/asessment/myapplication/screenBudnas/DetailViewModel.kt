package org.d3if3049.asessment.myapplication.screenBudnas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if3049.asessment.myapplication.database.BudnasDao
import org.d3if3049.asessment.myapplication.model.Budnas

class DetailViewModel(private val dao: BudnasDao) : ViewModel() {
    fun insert(judul: String, catatan: String, lokasi: String, lokasi2: String, gambar: String?) {
        val budnas = Budnas(
            judul = judul,
            catatan = catatan,
            lokasi = lokasi, // Menyimpan nilai lokasi
            lokasi2 = lokasi2,
            gambar = gambar
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(budnas)
        }
    }

    suspend fun getBudnas(judul: String): Budnas? {
        return dao.getBudnasById(judul)
    }

    fun update(judul: String, catatan: String, lokasi: String, lokasi2: String, gambar: String?) {
        val budnas = Budnas(
            judul = judul,
            catatan = catatan,
            lokasi = lokasi, // Menyimpan nilai lokasi
            lokasi2 = lokasi2,
            gambar = gambar
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.update(budnas)
        }
    }

    fun delete(judul: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteById(judul)
        }
    }
}