package org.d3if3049.mopro1.budnas.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budnas")
class Budnas (
    @PrimaryKey
    val judul: String,
    val catatan: String,
    val lokasi: String,
    val lokasi2: String,
    val gambar: String?
)
