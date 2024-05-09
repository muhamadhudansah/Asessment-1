package org.d3if3049.mopro1.budnas.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.d3if3049.mopro1.budnas.model.Budnas

@Dao
interface BudnasDao {
    @Insert
    suspend fun insert(budnas: Budnas)
    @Update
    suspend fun update(budnas: Budnas)
    @Query("SELECT * FROM budnas ORDER BY judul DESC")
    fun getBudnas(): Flow<List<Budnas>>
    @Query("SELECT * FROM budnas WHERE judul = :judul")
    suspend fun getBudnasById(judul: String): Budnas?
    @Query("DELETE FROM budnas WHERE judul = :judul")
    suspend fun deleteById(judul: String)


}