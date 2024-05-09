package org.d3if3049.mopro1.budnas.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.d3if3049.mopro1.budnas.model.Budnas

@Database(entities = [Budnas::class], version = 1, exportSchema = false)
abstract class BudnasDb : RoomDatabase() {
    abstract  val dao : BudnasDao
    companion object {
        @Volatile
        private var INSTANCE: BudnasDb? = null
        fun getInstance(context: Context): BudnasDb {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        BudnasDb::class.java,
                        "budnas.db"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
