package com.health.diafit.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.health.diafit.models.HistoryEntity

@Database(entities = [HistoryEntity::class], version = 1, exportSchema = false)
abstract class GlucoDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
}